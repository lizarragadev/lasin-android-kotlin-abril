package bo.edu.umsa.curso.clase04.mvvm.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import bo.edu.umsa.curso.clase04.mvvm.data.local.NoteEntity
import bo.edu.umsa.curso.clase04.mvvm.data.repository.NotesRepository
import bo.edu.umsa.curso.clase04.mvvm.domain.AppResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.zip

class NotesViewModel(
    private val repository: NotesRepository
) : ViewModel() {

    private val _query = MutableStateFlow("")
    private val _onlyPending = MutableStateFlow(true)
    private val _loading = MutableStateFlow(false)
    private val _error = MutableStateFlow<String?>(null)

    private val _events = MutableSharedFlow<String>()
    val events = _events.asSharedFlow()

    @OptIn(FlowPreview::class)
    private val debouncedQuery = _query
        .debounce(300)
        .map { it.trim() }
        .distinctUntilChanged()

    private val filteredNotes = combine(
        repository.observeNotes(onlyPending = true),
        repository.observeNotes(onlyPending = false),
        debouncedQuery,
        _onlyPending
    ) { pending, all, query, onlyPending ->
        val source = if (onlyPending) pending else all
        if (query.isBlank()) {
            source
        } else {
            source.filter {
                it.title.contains(query, ignoreCase = true) ||
                    it.description.contains(query, ignoreCase = true)
            }
        }
    }

    private val baseUiState = combine(
        filteredNotes,
        _query,
        _onlyPending,
        _loading
    ) { notes, query, onlyPending, loading ->
        NotesUiState(
            isLoading = loading,
            query = query,
            onlyPending = onlyPending,
            notes = notes
        )
    }

    val uiState: StateFlow<NotesUiState> = combine(baseUiState, _error) { base, error ->
        base.copy(errorMessage = error)
    }
        .flowOn(Dispatchers.Default)
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = NotesUiState()
        )

    fun onQueryChange(value: String) {
        _query.value = value
    }

    fun onOnlyPendingChange(value: Boolean) {
        _onlyPending.value = value
    }

    fun addNote(title: String, description: String) {
        viewModelScope.launch {
            repository.addNote(title, description)
        }
    }

    fun toggleDone(note: NoteEntity) {
        viewModelScope.launch {
            repository.toggleDone(note)
        }
    }

    fun syncRemote() {
        viewModelScope.launch {
            _loading.value = true
            _error.value = null
            when (val result = repository.syncRemoteTodos()) {
                is AppResult.Success -> _events.emit("Se importaron ${result.data} tareas")
                is AppResult.Error -> _error.value = result.message
            }
            _loading.value = false
        }
    }

    fun clearError() {
        _error.value = null
    }

    fun explainZipForClass() {
        viewModelScope.launch {
            _query.zip(_onlyPending) { query, onlyPending ->
                "ZIP demo -> query='$query' pending=$onlyPending"
            }.collect { message ->
                _events.emit(message)
            }
        }
    }
}

class NotesViewModelFactory(
    private val repository: NotesRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NotesViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return NotesViewModel(repository) as T
        }
        throw IllegalArgumentException("ViewModel no soportado: ${modelClass.name}")
    }
}

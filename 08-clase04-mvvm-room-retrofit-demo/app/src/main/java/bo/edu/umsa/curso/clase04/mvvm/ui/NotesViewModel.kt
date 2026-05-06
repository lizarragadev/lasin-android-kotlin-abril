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

/**
 * ViewModel que gestiona la lógica de negocio y el estado para la pantalla de Notas.
 */
class NotesViewModel(
    // Inyección del repositorio para acceder a los datos
    private val repository: NotesRepository
) : ViewModel() {

    // Estado interno para la consulta de búsqueda
    private val _query = MutableStateFlow("")
    // Estado interno para filtrar solo pendientes
    private val _onlyPending = MutableStateFlow(true)
    // Estado interno para indicar carga
    private val _loading = MutableStateFlow(false)
    // Estado interno para almacenar mensajes de error
    private val _error = MutableStateFlow<String?>(null)

    // Flujo para eventos de un solo uso (como mensajes Toast o SnackBar)
    private val _events = MutableSharedFlow<String>()
    val events = _events.asSharedFlow()

    // Consulta con debounce para evitar búsquedas excesivas mientras el usuario escribe
    @OptIn(FlowPreview::class)
    private val debouncedQuery = _query
        .debounce(300) // Espera 300ms de inactividad
        .map { it.trim() } // Elimina espacios en blanco
        .distinctUntilChanged() // Solo emite si el valor ha cambiado

    // Flujo que combina la base de datos, la búsqueda y el filtro de pendientes
    private val filteredNotes = combine(
        repository.observeNotes(onlyPending = true),
        repository.observeNotes(onlyPending = false),
        debouncedQuery,
        _onlyPending
    ) { pending, all, query, onlyPending ->
        // Selecciona la fuente según el filtro de pendientes
        val source = if (onlyPending) pending else all
        // Aplica el filtro de búsqueda por título o descripción
        if (query.isBlank()) {
            source
        } else {
            source.filter {
                it.title.contains(query, ignoreCase = true) ||
                    it.description.contains(query, ignoreCase = true)
            }
        }
    }

    // Combina todos los flujos para crear un estado base de UI
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

    // Estado final expuesto a la UI como un StateFlow
    val uiState: StateFlow<NotesUiState> = combine(baseUiState, _error) { base, error ->
        base.copy(errorMessage = error)
    }
        .flowOn(Dispatchers.Default) // Ejecuta la combinación en un hilo de computación
        .stateIn(
            scope = viewModelScope, // Vincula la vida del flujo al ViewModel
            started = SharingStarted.WhileSubscribed(5_000), // Se mantiene activo 5s tras perder suscriptores
            initialValue = NotesUiState() // Estado inicial
        )

    /**
     * Actualiza el valor de la búsqueda.
     */
    fun onQueryChange(value: String) {
        _query.value = value
    }

    /**
     * Cambia el filtro de notas pendientes.
     */
    fun onOnlyPendingChange(value: Boolean) {
        _onlyPending.value = value
    }

    /**
     * Agrega una nueva nota de forma asíncrona.
     */
    fun addNote(title: String, description: String) {
        viewModelScope.launch {
            repository.addNote(title, description)
        }
    }

    /**
     * Alterna el estado de finalización de una nota.
     */
    fun toggleDone(note: NoteEntity) {
        viewModelScope.launch {
            repository.toggleDone(note)
        }
    }

    /**
     * Sincroniza datos con la API remota.
     */
    fun syncRemote() {
        viewModelScope.launch {
            _loading.value = true // Activa indicador de carga
            _error.value = null // Limpia errores previos
            // Llama al repositorio y gestiona el resultado
            when (val result = repository.syncRemoteTodos()) {
                is AppResult.Success -> _events.emit("Se importaron ${result.data} tareas")
                is AppResult.Error -> _error.value = result.message
            }
            _loading.value = false // Desactiva indicador de carga
        }
    }

    /**
     * Limpia el mensaje de error actual.
     */
    fun clearError() {
        _error.value = null
    }

    /**
     * Función demostrativa sobre el uso de ZIP en Coroutines.
     */
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

/**
 * Fábrica para crear instancias de NotesViewModel con sus dependencias.
 */
class NotesViewModelFactory(
    private val repository: NotesRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        // Verifica si el modelClass es el correcto para este ViewModel
        if (modelClass.isAssignableFrom(NotesViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return NotesViewModel(repository) as T
        }
        throw IllegalArgumentException("ViewModel no soportado: ${modelClass.name}")
    }
}

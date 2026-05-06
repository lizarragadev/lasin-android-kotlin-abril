package bo.edu.umsa.curso.clase04.mvvm.ui

import androidx.compose.runtime.currentComposer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import bo.edu.umsa.curso.clase04.mvvm.data.repository.NotesRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class NotesViewModel(
    private val repository: NotesRepository
): ViewModel() {
    private val _query = MutableStateFlow("")
    private val _onlyPending = MutableStateFlow(true)
    private val _loading = MutableStateFlow(false)
    private val _error = MutableStateFlow<String?>(null)
    private val _uiState = MutableStateFlow(NotesUiState())
    val uiState: StateFlow<NotesUiState> = _uiState.asStateFlow()

     fun onQueryChange(newQuery: String) {
         _uiState.update { currentState ->
             currentState.copy(query = newQuery)
         }
     }

     fun onOnlyPendingChange(onlyPending: Boolean) {
         _uiState.update { currentState ->
             currentState.copy(onlyPending = onlyPending)
         }
     }

     fun onAddNoteClick() {
         // Lógica para agregar una nota
     }

     fun onSyncClick() {
         // Lógica para sincronizar con la API
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
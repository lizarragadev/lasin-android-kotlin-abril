package bo.edu.umsa.curso.clase04.mvvm.ui

import androidx.compose.runtime.currentComposer
import androidx.lifecycle.ViewModel
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
package bo.edu.umsa.curso.clase04.mvvm.ui

import bo.edu.umsa.curso.clase04.mvvm.data.local.NoteEntity

/**
 * Representa el estado de la interfaz de usuario de la pantalla de notas.
 */
data class NotesUiState(
    // Indica si se está cargando información
    val isLoading: Boolean = false,
    // El texto de búsqueda actual ingresado por el usuario
    val query: String = "",
    // Indica si se deben mostrar solo las notas pendientes
    val onlyPending: Boolean = true,
    // Lista de notas que se muestran actualmente en la pantalla
    val notes: List<NoteEntity> = emptyList(),
    // Mensaje de error opcional si algo falla
    val errorMessage: String? = null
)

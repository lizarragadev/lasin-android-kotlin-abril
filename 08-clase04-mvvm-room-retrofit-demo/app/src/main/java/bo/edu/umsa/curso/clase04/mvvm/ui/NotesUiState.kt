package bo.edu.umsa.curso.clase04.mvvm.ui

import bo.edu.umsa.curso.clase04.mvvm.data.local.NoteEntity

data class NotesUiState(
    val isLoading: Boolean = false,
    val query: String = "",
    val onlyPending: Boolean = true,
    val notes: List<NoteEntity> = emptyList(),
    val errorMessage: String? = null
)

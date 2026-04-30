package bo.edu.umsa.curso.clase04.mvvm.ui

data class NotesUiState(
    val isLoading: Boolean = false,
    val query: String = "",
    val onlyPending: Boolean = false,
    val errorMessage: String? = null
)
package bo.edu.umsa.curso.clase04.mvvm.domain

/**
 * Representa el resultado de una operación que puede ser exitosa o fallida.
 * @param T El tipo de datos devuelto en caso de éxito.
 */
sealed interface AppResult<out T> {
    // Caso de éxito que contiene los datos resultantes
    data class Success<T>(val data: T) : AppResult<T>
    // Caso de error que contiene un mensaje descriptivo
    data class Error(val message: String) : AppResult<Nothing>
}

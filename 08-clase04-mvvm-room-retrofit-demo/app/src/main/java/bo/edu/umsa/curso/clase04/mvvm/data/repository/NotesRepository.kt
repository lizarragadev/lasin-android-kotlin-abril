package bo.edu.umsa.curso.clase04.mvvm.data.repository

import bo.edu.umsa.curso.clase04.mvvm.data.local.NoteDao
import bo.edu.umsa.curso.clase04.mvvm.data.local.NoteEntity
import bo.edu.umsa.curso.clase04.mvvm.data.remote.RemoteApi
import bo.edu.umsa.curso.clase04.mvvm.domain.AppResult
import java.io.IOException
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import retrofit2.HttpException

/**
 * Repositorio que gestiona la fuente de datos de las notas.
 * Centraliza el acceso a la base de datos local y a la API remota.
 */
class NotesRepository(
    // Acceso a la base de datos local
    private val dao: NoteDao,
    // Acceso a la API remota
    private val api: RemoteApi,
    // Dispatcher para operaciones de Entrada/Salida
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) {
    /**
     * Observa el flujo de notas filtradas.
     */
    fun observeNotes(onlyPending: Boolean): Flow<List<NoteEntity>> = dao.observeNotes(onlyPending)

    /**
     * Realiza una búsqueda simple por texto en el repositorio local.
     */
    fun searchWithLike(query: String): Flow<List<NoteEntity>> = dao.searchNotesWithLike(query)

    /**
     * Agrega una nueva nota a la base de datos local.
     */
    suspend fun addNote(title: String, description: String) = withContext(ioDispatcher) {
        // Inserta la entidad nota con los datos proporcionados
        dao.insertNote(
            NoteEntity(
                title = title,
                description = description
            )
        )
    }

    /**
     * Cambia el estado 'hecho' de una nota existente.
     */
    suspend fun toggleDone(note: NoteEntity) = withContext(ioDispatcher) {
        // Actualiza la nota invirtiendo el valor de isDone
        dao.updateNote(note.copy(isDone = !note.isDone))
    }

    /**
     * Sincroniza las tareas desde la API remota e inserta los resultados localmente.
     * @return Un objeto AppResult con el conteo de notas importadas o el error.
     */
    suspend fun syncRemoteTodos(): AppResult<Int> = withContext(ioDispatcher) {
        try {
            // Obtiene las tareas desde el servicio remoto
            val remoteTodos = api.getTodos(limit = 6)
            // Ejecuta las inserciones de forma concurrente
            coroutineScope {
                remoteTodos.map { todo ->
                    async {
                        // Mapea el DTO remoto a la Entidad local e inserta
                        dao.insertNote(
                            NoteEntity(
                                title = todo.title,
                                description = "Importado desde API",
                                isDone = todo.completed,
                                priority = 2
                            )
                        )
                    }
                }.forEach { it.await() } // Espera a que todas las inserciones finalicen
            }
            // Retorna éxito con la cantidad de elementos procesados
            AppResult.Success(remoteTodos.size)
        } catch (e: IOException) {
            // Maneja errores de conectividad
            AppResult.Error("Sin internet o timeout: ${e.message.orEmpty()}")
        } catch (e: HttpException) {
            // Maneja errores de respuesta de la API (4xx, 5xx)
            AppResult.Error("Error HTTP ${e.code()}: ${e.message.orEmpty()}")
        } catch (e: Exception) {
            // Maneja cualquier otro error inesperado
            AppResult.Error("Error inesperado: ${e.message.orEmpty()}")
        }
    }
}

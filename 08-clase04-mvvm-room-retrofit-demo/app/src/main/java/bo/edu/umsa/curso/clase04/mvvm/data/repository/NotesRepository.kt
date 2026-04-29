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

class NotesRepository(
    private val dao: NoteDao,
    private val api: RemoteApi,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) {
    fun observeNotes(onlyPending: Boolean): Flow<List<NoteEntity>> = dao.observeNotes(onlyPending)

    fun searchWithLike(query: String): Flow<List<NoteEntity>> = dao.searchNotesWithLike(query)

    suspend fun addNote(title: String, description: String) = withContext(ioDispatcher) {
        dao.insertNote(
            NoteEntity(
                title = title,
                description = description
            )
        )
    }

    suspend fun toggleDone(note: NoteEntity) = withContext(ioDispatcher) {
        dao.updateNote(note.copy(isDone = !note.isDone))
    }

    suspend fun syncRemoteTodos(): AppResult<Int> = withContext(ioDispatcher) {
        try {
            val remoteTodos = api.getTodos(limit = 6)
            coroutineScope {
                remoteTodos.map { todo ->
                    async {
                        dao.insertNote(
                            NoteEntity(
                                title = todo.title,
                                description = "Importado desde API",
                                isDone = todo.completed,
                                priority = 2
                            )
                        )
                    }
                }.forEach { it.await() }
            }
            AppResult.Success(remoteTodos.size)
        } catch (e: IOException) {
            AppResult.Error("Sin internet o timeout: ${e.message.orEmpty()}")
        } catch (e: HttpException) {
            AppResult.Error("Error HTTP ${e.code()}: ${e.message.orEmpty()}")
        } catch (e: Exception) {
            AppResult.Error("Error inesperado: ${e.message.orEmpty()}")
        }
    }
}

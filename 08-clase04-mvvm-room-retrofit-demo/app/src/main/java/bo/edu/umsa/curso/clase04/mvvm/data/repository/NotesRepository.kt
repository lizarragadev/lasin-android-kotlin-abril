package bo.edu.umsa.curso.clase04.mvvm.data.repository

import bo.edu.umsa.curso.clase04.mvvm.data.local.NoteDao
import bo.edu.umsa.curso.clase04.mvvm.data.local.NoteEntity
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class NotesRepository(
    private val dao: NoteDao,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) {
    fun observeNotes(onlyPending: Boolean): Flow<List<NoteEntity>> = dao.observeNotes(onlyPending)

    fun searchWithLike(query: String): Flow<List<NoteEntity>> = dao.searchNotesWithLike(query)

    suspend fun addNote(title: String, description: String) = withContext(ioDispatcher) {
        dao.insertNote(
            NoteEntity(title = title, description = description, isDone = false, priority = 1)
        )
    }

    suspend fun toggleDone(note: NoteEntity) = withContext(ioDispatcher) {
        dao.updateNote(note.copy(isDone = !note.isDone))
    }
}
package bo.edu.umsa.curso.clase04.mvvm.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNote(note: NoteEntity): Long

    @Update
    suspend fun updateNote(note: NoteEntity)

    @Delete
    suspend fun deleteNote(note: NoteEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCategory(category: CategoryEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCrossRef(crossRef: NoteCategoryCrossRef)

    @Query(
        """
        SELECT * FROM notes
        WHERE (:onlyPending = 0 OR is_done = 0)
        ORDER BY priority DESC, id DESC
        """
    )
    fun observeNotes(onlyPending: Boolean): Flow<List<NoteEntity>>

    @Transaction
    @Query("SELECT * FROM notes ORDER BY id DESC")
    fun observeNotesWithCategories(): Flow<List<NoteWithCategories>>

    @Query(
        """
        SELECT n.* FROM notes n
        INNER JOIN notes_fts f ON n.id = f.rowid
        WHERE notes_fts MATCH :term
        ORDER BY n.priority DESC
        """
    )
    fun searchNotes(term: String): Flow<List<NoteEntity>>

    @Query(
        """
        SELECT * FROM notes
        WHERE title LIKE '%' || :query || '%' OR description LIKE '%' || :query || '%'
        ORDER BY id DESC
        """
    )
    fun searchNotesWithLike(query: String): Flow<List<NoteEntity>>
}

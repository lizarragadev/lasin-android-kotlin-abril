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
interface NoteDao{
       @Insert(onConflict = OnConflictStrategy.REPLACE)
       suspend fun insertNote(note: NoteEntity): Long

       @Update
       suspend fun updateNote(note: NoteEntity)

       @Delete
       suspend fun deleteNote(note: NoteEntity)

       @Insert(onConflict = OnConflictStrategy.REPLACE)
       suspend fun insertCategory(category: CategoryEntity): Long

       @Insert(onConflict = OnConflictStrategy.REPLACE)
       suspend fun insertNoteCategoryCrossRef(crossRef: NoteCategoryCrossRef)

       @Query("""
           SELECT * FROM notes
           WHERE (:onlyPending = 0 OR is_done = 0)
           ORDER BY priority DESC, id DESC
       """)
       fun observeNotes(onlyPending: Boolean): Flow<List<NoteEntity>>

       @Transaction
       @Query("SELECT * FROM notes ORDER BY id DESC")
       suspend fun getNotesWithCategories(): Flow<List<NoteWithCategories>>

        

}
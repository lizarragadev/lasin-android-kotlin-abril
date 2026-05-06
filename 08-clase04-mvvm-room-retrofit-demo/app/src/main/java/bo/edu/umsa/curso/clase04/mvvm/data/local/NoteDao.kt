package bo.edu.umsa.curso.clase04.mvvm.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

/**
 * Interfaz de Acceso a Datos (DAO) para realizar operaciones sobre las notas en la base de datos.
 */
@Dao
interface NoteDao {

    // Inserta una nota; si hay conflicto de ID, la reemplaza
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNote(note: NoteEntity): Long

    // Actualiza los campos de una nota existente
    @Update
    suspend fun updateNote(note: NoteEntity)

    // Elimina una nota de la base de datos
    @Delete
    suspend fun deleteNote(note: NoteEntity)

    // Inserta una categoría en la base de datos
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCategory(category: CategoryEntity): Long

    // Inserta la relación entre una nota y una categoría
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCrossRef(crossRef: NoteCategoryCrossRef)

    /**
     * Observa cambios en las notas. 
     * @param onlyPending Si es verdadero, filtra solo las notas que no están completas.
     * Devuelve un Flow que emite una nueva lista cada vez que cambian los datos.
     */
    @Query(
        """
        SELECT * FROM notes
        WHERE (:onlyPending = 0 OR is_done = 0)
        ORDER BY priority DESC, id DESC
        """
    )
    fun observeNotes(onlyPending: Boolean): Flow<List<NoteEntity>>

    /**
     * Obtiene todas las notas incluyendo sus categorías asociadas.
     * @Transaction asegura que la operación sea atómica al consultar múltiples tablas.
     */
    @Transaction
    @Query("SELECT * FROM notes ORDER BY id DESC")
    fun observeNotesWithCategories(): Flow<List<NoteWithCategories>>

    /**
     * Realiza una búsqueda de texto completo (FTS) utilizando el índice de FTS.
     * @param term El término de búsqueda a coincidir.
     */
    @Query(
        """
        SELECT n.* FROM notes n
        INNER JOIN notes_fts f ON n.id = f.rowid
        WHERE notes_fts MATCH :term
        ORDER BY n.priority DESC
        """
    )
    fun searchNotes(term: String): Flow<List<NoteEntity>>

    /**
     * Realiza una búsqueda de texto simple utilizando el operador LIKE.
     * @param query El texto a buscar en el título o descripción.
     */
    @Query(
        """
        SELECT * FROM notes
        WHERE title LIKE '%' || :query || '%' OR description LIKE '%' || :query || '%'
        ORDER BY id DESC
        """
    )
    fun searchNotesWithLike(query: String): Flow<List<NoteEntity>>
}

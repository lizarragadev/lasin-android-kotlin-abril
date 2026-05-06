package bo.edu.umsa.curso.clase04.mvvm.data.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Fts4
import androidx.room.ForeignKey
import androidx.room.Ignore
import androidx.room.Index
import androidx.room.Junction
import androidx.room.PrimaryKey
import androidx.room.Relation
import androidx.room.Embedded

/**
 * Entidad que representa una categoría de notas en la base de datos.
 */
@Entity(tableName = "categories")
data class CategoryEntity(
    // Identificador único autogenerado
    @PrimaryKey(autoGenerate = true) val id: Long = 0L,
    // Nombre de la categoría
    val name: String
)

/**
 * Entidad que representa una nota en la base de datos.
 * Incluye índices para mejorar el rendimiento de las búsquedas por título y prioridad.
 */
@Entity(
    tableName = "notes",
    indices = [Index("title"), Index("priority")]
)
data class NoteEntity(
    // Identificador único autogenerado
    @PrimaryKey(autoGenerate = true) val id: Long = 0L,
    // Título de la nota
    @ColumnInfo(name = "title") val title: String,
    // Descripción detallada de la nota
    @ColumnInfo(name = "description") val description: String,
    // Indica si la nota ha sido completada
    @ColumnInfo(name = "is_done") val isDone: Boolean = false,
    // Nivel de prioridad de la nota
    @ColumnInfo(name = "priority") val priority: Int = 1
) {
    // Campo ignorado por Room, usado solo para formatear la prioridad en la UI
    @Ignore
    val formattedPriority: String = "P$priority"
}

/**
 * Tabla de referencia cruzada para la relación muchos-a-muchos entre Notas y Categorías.
 * Define claves foráneas con eliminación en cascada.
 */
@Entity(
    tableName = "note_category_cross_ref",
    primaryKeys = ["noteId", "categoryId"],
    foreignKeys = [
        ForeignKey(
            entity = NoteEntity::class,
            parentColumns = ["id"],
            childColumns = ["noteId"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = CategoryEntity::class,
            parentColumns = ["id"],
            childColumns = ["categoryId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class NoteCategoryCrossRef(
    // ID de la nota relacionada
    val noteId: Long,
    // ID de la categoría relacionada
    val categoryId: Long
)

/**
 * Clase POJO que representa una nota junto con todas sus categorías asociadas.
 */
data class NoteWithCategories(
    // Embebe los datos de la nota
    @Embedded val note: NoteEntity,
    // Define la relación a través de la tabla de unión
    @Relation(
        parentColumn = "id",
        entityColumn = "id",
        associateBy = Junction(
            value = NoteCategoryCrossRef::class,
            parentColumn = "noteId",
            entityColumn = "categoryId"
        )
    )
    // Lista de categorías asociadas a la nota
    val categories: List<CategoryEntity>
)

/**
 * Entidad para soporte de búsqueda de texto completo (FTS4) sobre las notas.
 */
@Fts4(contentEntity = NoteEntity::class)
@Entity(tableName = "notes_fts")
data class NoteFts(
    // Mapeo al rowid de SQLite necesario para FTS
    @PrimaryKey
    @ColumnInfo(name = "rowid")
    val rowId: Long,
    // Título indexado para búsqueda rápida
    val title: String,
    // Descripción indexada para búsqueda rápida
    val description: String
)

package bo.edu.umsa.curso.clase04.mvvm.data.local

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Ignore
import androidx.room.Index
import androidx.room.Junction
import androidx.room.PrimaryKey
import androidx.room.Relation

@Entity(tableName = "categories")
data class CategoryEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    val name: String
)

@Entity(tableName = "notes", indices = [Index("title"), Index("priority")])
data class NoteEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0L,
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "description") val description: String,
    @ColumnInfo(name = "is_done") val isDone: Boolean,
    @ColumnInfo(name = "priority") val priority: Int,
) {
    @Ignore val formattedPriority: String = "P$priority"
}

@Entity(tableName = "note_category_cross_ref",
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
    ])
data class NoteCategoryCrossRef(
    val noteId: Long,
    val categoryId: Long
)

data class NoteWithCategories(
    @Embedded
    val note: NoteEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "id",
        associateBy = Junction(
            value = NoteCategoryCrossRef::class,
            parentColumn = "noteId",
            entityColumn = "categoryId"
        )
    )
    val categories: List<CategoryEntity>
)









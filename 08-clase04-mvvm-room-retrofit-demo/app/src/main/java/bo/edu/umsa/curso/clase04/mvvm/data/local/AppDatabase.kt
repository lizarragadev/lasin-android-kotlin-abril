package bo.edu.umsa.curso.clase04.mvvm.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

@Database(
    entities = [
        NoteEntity::class,
        CategoryEntity::class,
        NoteCategoryCrossRef::class,
        NoteFts::class
    ],
    version = 2,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun noteDao(): NoteDao

    companion object {
        val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(db: SupportSQLiteDatabase) {
                db.execSQL(
                    "ALTER TABLE notes ADD COLUMN priority INTEGER NOT NULL DEFAULT 1"
                )
            }
        }
    }
}

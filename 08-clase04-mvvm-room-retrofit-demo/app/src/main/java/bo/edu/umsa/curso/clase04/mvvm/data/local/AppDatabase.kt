package bo.edu.umsa.curso.clase04.mvvm.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

/**
 * Clase abstracta que define la base de datos de la aplicación utilizando Room.
 * Incluye las entidades y gestiona las versiones de la base de datos.
 */
@Database(
    entities = [
        NoteEntity::class,
        CategoryEntity::class,
        NoteCategoryCrossRef::class,
        NoteFts::class
    ],
    // Versión actual de la base de datos
    version = 2,
    // No exporta el esquema JSON para control de versiones
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    // Provee el DAO para interactuar con las notas
    abstract fun noteDao(): NoteDao

    companion object {
        /**
         * Define la migración de la versión 1 a la versión 2.
         * En este caso, añade la columna 'priority' a la tabla 'notes'.
         */
        val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(db: SupportSQLiteDatabase) {
                // Ejecuta SQL directo para añadir la nueva columna
                db.execSQL(
                    "ALTER TABLE notes ADD COLUMN priority INTEGER NOT NULL DEFAULT 1"
                )
            }
        }
    }
}

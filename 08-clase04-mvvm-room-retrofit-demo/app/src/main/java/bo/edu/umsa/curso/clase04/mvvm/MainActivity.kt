package bo.edu.umsa.curso.clase04.mvvm

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.padding
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.room.Room
import bo.edu.umsa.curso.clase04.mvvm.data.local.AppDatabase
import bo.edu.umsa.curso.clase04.mvvm.data.remote.RemoteServiceFactory
import bo.edu.umsa.curso.clase04.mvvm.data.repository.NotesRepository
import bo.edu.umsa.curso.clase04.mvvm.ui.NotesScreen
import bo.edu.umsa.curso.clase04.mvvm.ui.NotesViewModel
import bo.edu.umsa.curso.clase04.mvvm.ui.NotesViewModelFactory
import kotlinx.coroutines.flow.collectLatest

/**
 * Actividad principal de la aplicación que sirve como punto de entrada.
 * Configura la base de datos, el repositorio y la interfaz de usuario con Compose.
 */
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Habilita el diseño de borde a borde para aprovechar toda la pantalla
        enableEdgeToEdge()

        // Configuración de la base de datos Room de forma manual
        val db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "notes.db"
        )
            // Añade la migración definida en AppDatabase
            .addMigrations(AppDatabase.MIGRATION_1_2)
            .build()

        // Inicialización del repositorio inyectando el DAO y la API remota
        val repository = NotesRepository(
            dao = db.noteDao(),
            api = RemoteServiceFactory.createApi()
        )

        // Define el contenido de la actividad usando Jetpack Compose
        setContent {
            // Crea la fábrica para el ViewModel con el repositorio
            val factory = NotesViewModelFactory(repository)
            // Obtiene la instancia del ViewModel usando la fábrica
            val vm: NotesViewModel = viewModel(factory = factory)
            // Estado para gestionar el SnackBar (mensajes emergentes)
            val snackbarHostState = remember { SnackbarHostState() }

            // Efecto que se dispara una vez para escuchar eventos del ViewModel
            LaunchedEffect(Unit) {
                // Escucha los mensajes emitidos por el SharedFlow 'events'
                vm.events.collectLatest { message ->
                    // Muestra el mensaje en el SnackBar
                    snackbarHostState.showSnackbar(message)
                }
            }

            // Define el tema visual de la aplicación
            MaterialTheme {
                // Estructura básica de la pantalla con soporte para SnackBar
                Scaffold(
                    snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
                ) { innerPadding ->
                    // Carga la pantalla de notas pasando el ViewModel y el padding del Scaffold
                    NotesScreen(
                        viewModel = vm,
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

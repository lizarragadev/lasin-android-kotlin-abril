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

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "notes.db"
        )
            .addMigrations(AppDatabase.MIGRATION_1_2)
            .build()

        val repository = NotesRepository(
            dao = db.noteDao(),
            api = RemoteServiceFactory.createApi()
        )

        setContent {
            val factory = NotesViewModelFactory(repository)
            val vm: NotesViewModel = viewModel(factory = factory)
            val snackbarHostState = remember { SnackbarHostState() }

            LaunchedEffect(Unit) {
                vm.events.collectLatest { message ->
                    snackbarHostState.showSnackbar(message)
                }
            }

            MaterialTheme {
                Scaffold(
                    snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
                ) { innerPadding ->
                    NotesScreen(
                        viewModel = vm,
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

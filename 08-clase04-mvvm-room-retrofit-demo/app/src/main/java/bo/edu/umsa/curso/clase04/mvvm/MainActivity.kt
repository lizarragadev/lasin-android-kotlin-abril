package bo.edu.umsa.curso.clase04.mvvm

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import bo.edu.umsa.curso.clase04.mvvm.ui.NotesScreen
import bo.edu.umsa.curso.clase04.mvvm.ui.NotesUiState
import bo.edu.umsa.curso.clase04.mvvm.ui.NotesViewModel

// MVVM - Modelo Vista VistaModelo
// UI con Compose
//    |
// ViewModel
//    |
// Repository
//    |
// Data Sources

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MaterialTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    topBar = { Text("Notas") }
                ) { paddingValues ->
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(paddingValues)
                    ) {
                        val vm: NotesViewModel = viewModel()
                        NotesScreen(
                            viewModel = vm
                        )
                    }
                }
            }
        }
    }
}

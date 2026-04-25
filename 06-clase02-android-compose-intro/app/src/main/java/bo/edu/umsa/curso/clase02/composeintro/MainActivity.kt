package bo.edu.umsa.curso.clase02.composeintro

import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import bo.edu.umsa.curso.clase02.composeintro.ui.theme.Clase02ComposeIntroTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: android.os.Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Clase02ComposeIntroTheme {
                DemoComposeIntroScreen()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DemoComposeIntroScreen() {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = { Text("Demo Compose Intro") },
            )
        },
    ) {
        innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(all = 16.dp)
                .fillMaxSize()
        ) {
            Text("¡Hola, Compose!", style = MaterialTheme.typography.bodyLarge)
            Text("Texto Mediano", style = MaterialTheme.typography.bodyMedium)
            Text("Android", style = MaterialTheme.typography.bodySmall)
        }
    }
}





package bo.edu.umsa.curso.clase02.composeintro

import android.content.res.Configuration
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Alignment
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.School
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import bo.edu.umsa.curso.clase02.composeintro.ui.theme.Clase02ComposeIntroTheme

/**
 * Clase 2 (2.ª mitad) — Primer contacto con Jetpack Compose.
 *
 * - Demo simple: Text, Button, Icon, Preview
 * - Demo avanzada: estado con remember/rememberSaveable, OutlinedTextField, contador
 * - Más ejemplos: [SeccionEjemplosMaterial3] y Previews en [ComposeClase2Extras]
 */
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
                title = { Text("Compose — Clase 2") },
            )
        },
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(horizontal = 20.dp)
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            Text(
                text = "Bienvenido a Jetpack Compose",
                style = MaterialTheme.typography.headlineSmall,
            )
            Text(
                text = "Demo simple: Text, Button e Icon",
                style = MaterialTheme.typography.titleMedium,
            )
            Button(
                onClick = { /* solo visual en esta sección */ },
                modifier = Modifier.fillMaxWidth(),
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.School, contentDescription = null)
                    Text(
                        text = "Botón con ícono",
                        modifier = Modifier.padding(start = 8.dp),
                    )
                }
            }

            HorizontalDivider()

            Text(
                text = "Demo avanzada: estado + TextField",
                style = MaterialTheme.typography.titleMedium,
            )
            ContadorYNombre()

            HorizontalDivider()

            Text(
            )
            SeccionEjemplosMaterial3()
        }
    }
}

@Composable
private fun ContadorYNombre() {
    var nombre by rememberSaveable { mutableStateOf("") }
    var contador by rememberSaveable { mutableIntStateOf(0) }

    OutlinedTextField(
        value = nombre,
        onValueChange = { nombre = it },
        modifier = Modifier.fillMaxWidth(),
        label = { Text("Tu nombre") },
        singleLine = true,
    )

    Text(
        text = if (nombre.isBlank()) {
            "Hola — pulsa el botón para sumar"
        } else {
            "Hola $nombre, contador = $contador"
        },
        style = MaterialTheme.typography.bodyLarge,
    )

    Button(
        onClick = { contador++ },
        modifier = Modifier.fillMaxWidth(),
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(Icons.Default.Add, contentDescription = null)
            Text(
                text = "Incrementar",
                modifier = Modifier.padding(start = 8.dp),
            )
        }
    }
}

@Preview(name = "Claro", showBackground = true)
@Composable
private fun DemoPreviewClaro() {
    Clase02ComposeIntroTheme {
        DemoComposeIntroScreen()
    }
}

@Preview(
    name = "Oscuro",
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES,
)
@Composable
private fun DemoPreviewOscuro() {
    Clase02ComposeIntroTheme {
        DemoComposeIntroScreen()
    }
}

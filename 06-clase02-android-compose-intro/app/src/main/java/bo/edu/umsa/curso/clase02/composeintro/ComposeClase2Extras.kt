package bo.edu.umsa.curso.clase02.composeintro

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.AssistChip
import androidx.compose.material3.Card
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import bo.edu.umsa.curso.clase02.composeintro.ui.theme.Clase02ComposeIntroTheme

/**
 * Ejemplos extra para la misma sesión (Clase 2): Card, chips, Switch, texto secundario.
 * Útil para mostrar en Previews sin saturar [MainActivity].
 */
@Composable
fun SeccionEjemplosMaterial3(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        Text(
            text = "Más componentes (demo)",
            style = MaterialTheme.typography.titleMedium,
        )

        ElevatedCard {
            Column(Modifier.padding(16.dp)) {
                Text("ElevatedCard", style = MaterialTheme.typography.titleSmall)
                Text(
                    text = "Texto secundario con estilo bodyMedium.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            }
        }

        Card {
            Row(
                modifier = Modifier.padding(16.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Icon(Icons.Default.Info, contentDescription = null)
                Text(
                    text = "Card con fila e ícono",
                    modifier = Modifier.padding(start = 8.dp),
                )
            }
        }

        var notificaciones by rememberSaveable { mutableStateOf(true) }
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Text("Notificaciones")
            Switch(checked = notificaciones, onCheckedChange = { notificaciones = it })
        }

        var filtroSoloInf by rememberSaveable { mutableStateOf(false) }
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            FilterChip(
                selected = filtroSoloInf,
                onClick = { filtroSoloInf = !filtroSoloInf },
                label = { Text("Solo INF") },
            )
            AssistChip(
                onClick = { },
                label = { Text("Ayuda") },
            )
        }
    }
}

@Preview(name = "Extras — claro", showBackground = true)
@Composable
private fun PreviewExtrasClaro() {
    Clase02ComposeIntroTheme {
        SeccionEjemplosMaterial3(Modifier.padding(16.dp))
    }
}

@Preview(name = "Extras — oscuro", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun PreviewExtrasOscuro() {
    Clase02ComposeIntroTheme {
        SeccionEjemplosMaterial3(Modifier.padding(16.dp))
    }
}

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
 */
@Composable
fun SeccionEjemplosMaterial3(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.fillMaxWidth(),
    ) {
        Text(
                )
        
            ElevatedCard {
                Column(Modifier.padding(16.dp)) {
            }
        }
    }
}

        Row(
            modifier = Modifier.fillMaxWidth(),
        ) {

            FilterChip(
            )
            AssistChip(
                onClick = { },
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

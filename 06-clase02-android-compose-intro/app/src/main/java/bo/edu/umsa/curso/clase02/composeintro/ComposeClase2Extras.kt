package bo.edu.umsa.curso.clase02.composeintro

import android.content.res.Configuration
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.AssistChip
import androidx.compose.material3.Card
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.FilterChip
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import bo.edu.umsa.curso.clase02.composeintro.ui.theme.Clase02ComposeIntroTheme

/**
 * EXPLICACIÓN CONCEPTUAL (Para la clase):
 * 1. Paradigma Declarativo: Describimos QUÉ debe mostrar la pantalla según el estado actual,
 *    no CÓMO cambiar cada vista manualmente (imperativo).
 * 2. Recomposición: Compose vuelve a ejecutar las funciones @Composable cuando su estado cambia.
 */

@Composable
fun SeccionEjemplosMaterial3(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        // --- 1. Box, Image y Modificadores (background, clip, border) ---
        Text("1. Box, Image y Modificadores", style = MaterialTheme.typography.titleMedium)
        EjemploBoxEImagen()

        HorizontalDivider()

        // --- 2. State Hoisting y Animación básica ---
        Text("2. Hoisting y Animaciones", style = MaterialTheme.typography.titleMedium)
        var visible by rememberSaveable { mutableStateOf(true) }
        EjemploHoistingYAnimacion(
            estaVisible = visible,
            onToggle = { visible = !visible }
        )

        HorizontalDivider()

        // --- 3. Componentes de Selección y weight ---
        Text("3. Chips, Switch y weight", style = MaterialTheme.typography.titleMedium)
        EjemploSeleccionYWeight()

        HorizontalDivider()

        // --- 4. Listas Eficientes (LazyColumn y LazyRow) ---
        Text("4. Listas Eficientes", style = MaterialTheme.typography.titleMedium)
        EjemploLazyList()
    }
}

@Composable
fun EjemploBoxEImagen() {
    // El orden de los modificadores IMPORTA:
    // padding -> background aplica fondo después del margen.
    // background -> padding aplica fondo y luego el margen interno.
    Box(
        modifier = Modifier
            .size(100.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(MaterialTheme.colorScheme.primaryContainer)
            .border(2.dp, MaterialTheme.colorScheme.primary, RoundedCornerShape(16.dp))
            .clickable { /* Acción */ },
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_launcher_foreground),
            contentDescription = "Logo",
            modifier = Modifier.size(80.dp)
        )
        // Box permite superponer elementos
        Text(
            "M3",
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(4.dp)
                .background(Color.Black.copy(alpha = 0.5f), CircleShape)
                .padding(horizontal = 6.dp),
            color = Color.White,
            style = MaterialTheme.typography.labelSmall
        )
    }
}

@Composable
fun EjemploHoistingYAnimacion(estaVisible: Boolean, onToggle: () -> Unit) {
    val colorAnimado by animateColorAsState(
        targetValue = if (estaVisible) MaterialTheme.colorScheme.primary else Color.Gray,
        label = "color"
    )

    Column {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text("Mostrar contenido:", color = colorAnimado)
            IconButton(onClick = onToggle) {
                Icon(
                    if (estaVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                    contentDescription = null,
                    tint = colorAnimado
                )
            }
        }
        
        AnimatedVisibility(visible = estaVisible) {
            ElevatedCard {
                Column(Modifier.padding(16.dp)) {
                    Text("¡Contenido animado!", style = MaterialTheme.typography.titleSmall)
                    Text("Este card aparece/desaparece con AnimatedVisibility y cambia de color.")
                }
            }
        }
    }
}

@Composable
fun EjemploSeleccionYWeight() {
    var check by rememberSaveable { mutableStateOf(true) }
    var filtro by rememberSaveable { mutableStateOf(false) }

    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Switch(checked = check, onCheckedChange = { check = it })

            FilterChip(
                selected = filtro,
                onClick = { filtro = !filtro },
                label = { Text("Filtro") }
            )

            AssistChip(
                onClick = { },
                label = { Text("Ayuda") }
            )
        }

        // Ejemplo de Modifier.weight
        Row(modifier = Modifier.fillMaxWidth()) {
            Text(
                "Peso 1",
                modifier = Modifier
                    .weight(1f)
                    .background(Color.LightGray)
                    .padding(8.dp)
            )
            Text(
                "Peso 2",
                modifier = Modifier
                    .weight(2f)
                    .background(Color.Gray)
                    .padding(8.dp)
            )
        }
    }
}

@Composable
fun EjemploLazyList() {
    val items = List(20) { "Item #${it + 1}" }
    
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Text("LazyRow:", style = MaterialTheme.typography.labelMedium)
        LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            items(items) { item ->
                AssistChip(onClick = {}, label = { Text(item) })
            }
        }

        Text("LazyColumn:", style = MaterialTheme.typography.labelMedium)
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(4.dp),
            modifier = Modifier.height(100.dp).fillMaxWidth()
        ) {
            items(items) { item ->
                Card(modifier = Modifier.fillMaxWidth()) {
                    Text(item, modifier = Modifier.padding(8.dp))
                }
            }
        }
        Text("(Nota: LazyVerticalGrid funciona similar)", style = MaterialTheme.typography.labelSmall)
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

package bo.edu.umsa.curso.clase02.composeintro

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AssistChip
import androidx.compose.material3.Card
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.FilterChip
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
import androidx.compose.ui.unit.dp

@Composable
fun SeccionEjemploMaterial3() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text("Ejemplo de Material 3",
            style = MaterialTheme.typography.titleMedium)
        EjemploBoxImagen()
        EjemploCard()
        EjemploSeleccion()
        EjemploLazyList()
    }
}

@Composable
fun EjemploLazyList() {
    val elements = List(20) { "Item #${it + 1}" }
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Text("LazyRow", style = MaterialTheme.typography.labelMedium)
        LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            items(elements) { item ->
                AssistChip(
                    onClick = {  },
                    label = { Text(item) }
                )
            }
        }
        Text("LazyColumn", style = MaterialTheme.typography.labelMedium)
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.height(500.dp).fillMaxWidth()
        ) {
            items(elements) { item ->
                Card(modifier = Modifier.fillMaxWidth()) {
                    Text(item, modifier = Modifier.padding(8.dp))
                }
            }
        }
    }
}

@Composable
fun EjemploSeleccion() {
    var check by rememberSaveable { mutableStateOf(true) }
    var filtro by rememberSaveable { mutableStateOf(false) }
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Switch(
                checked = check,
                onCheckedChange = { check = it }
            )
            FilterChip(
                selected = filtro,
                onClick = { filtro = !filtro },
                label = { Text("Filtro") }
            )
            AssistChip(
                onClick = { /* Acción del chip */ },
                label = { Text("Asistencia") }
            )
        }
        Row(modifier = Modifier.fillMaxWidth()) {
            Text("Peso 1", modifier = Modifier
                .weight(3f)
                .background(Color.LightGray)
                .padding(8.dp)
            )
            Text("Peso 2", modifier = Modifier
                .weight(2f)
                .background(Color.LightGray)
                .padding(8.dp)
            )
        }
    }
}

@Composable
fun EjemploCard() {
    Column {
        ElevatedCard {
            Column(Modifier.padding(16.dp)) {
                Text("Título de la tarjeta", style = MaterialTheme.typography.titleMedium)
                Text("Descripción de la tarjeta", style = MaterialTheme.typography.bodyMedium)
            }
        }
    }
}

@Composable
fun EjemploBoxImagen() {
    Box(
        modifier = Modifier
            .size(160.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(MaterialTheme.colorScheme.primaryContainer)
            .border(2.dp, MaterialTheme.colorScheme.primary)
            .clickable {  },
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_launcher_foreground),
            contentDescription = "Ejemplo de imagen",
            modifier = Modifier.size(80.dp)
        )
        Text("M3", modifier = Modifier
            .align(Alignment.BottomEnd)
            .padding(4.dp)
            .background(Color.Black)
        )
    }
}
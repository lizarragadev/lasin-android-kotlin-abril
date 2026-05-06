package bo.edu.umsa.curso.clase04.mvvm.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle

/**
 * Pantalla principal que muestra la lista de notas y controles de filtrado.
 */
@Composable
fun NotesScreen(
    viewModel: NotesViewModel, // ViewModel que provee el estado y las acciones
    modifier: Modifier = Modifier
) {
    // Recolecta el estado del ViewModel de forma segura para el ciclo de vida de Compose
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    // Estado local para controlar la visibilidad del diálogo de nueva nota
    var showDialog by remember { mutableStateOf(false) }

    // Muestra el diálogo si showDialog es verdadero
    if (showDialog) {
        AddNoteDialog(
            onDismiss = { showDialog = false }, // Cierra el diálogo al cancelar
            onConfirm = { title, description ->
                viewModel.addNote(title, description) // Agrega la nota mediante el ViewModel
                showDialog = false // Cierra el diálogo tras confirmar
            }
        )
    }

    // Contenedor principal en columna con espaciado entre elementos
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        // Campo de texto para buscar notas con efecto debounce
        OutlinedTextField(
            value = state.query,
            onValueChange = viewModel::onQueryChange, // Notifica cambios al ViewModel
            label = { Text("Buscar con debounce") },
            modifier = Modifier.fillMaxWidth()
        )

        // Fila que contiene el filtro de notas pendientes
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text("Solo pendientes")
            Switch(
                checked = state.onlyPending,
                onCheckedChange = viewModel::onOnlyPendingChange // Cambia el filtro en el ViewModel
            )
        }

        // Fila de botones de acción
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Button(onClick = { showDialog = true }) {
                Text("Agregar")
            }
            Button(onClick = viewModel::syncRemote) { // Inicia sincronización con API
                Text("Sync API")
            }
        }

        // Muestra mensaje de error si existe en el estado
        state.errorMessage?.let {
            Text(text = it, color = MaterialTheme.colorScheme.error)
        }

        // Muestra un indicador de carga circular si está cargando
        if (state.isLoading) {
            CircularProgressIndicator()
        }

        // Lista perezosa (LazyColumn) para mostrar las notas eficientemente
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            // Itera sobre la lista de notas del estado
            items(state.notes, key = { it.id }) { note ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        // Muestra el título y la descripción de la nota
                        Text(note.title, style = MaterialTheme.typography.titleMedium)
                        Text(note.description, style = MaterialTheme.typography.bodyMedium)
                    }
                    // Botón para completar o reabrir la nota
                    Button(onClick = { viewModel.toggleDone(note) }) {
                        Text(if (note.isDone) "Reabrir" else "Completar")
                    }
                }
            }
        }
    }
}

/**
 * Diálogo composable para capturar los datos de una nueva nota.
 */
@Composable
fun AddNoteDialog(
    onDismiss: () -> Unit, // Acción al cancelar o cerrar
    onConfirm: (String, String) -> Unit // Acción al confirmar con datos
) {
    // Estados locales para los campos del formulario
    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Nueva Nota") },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                // Campo para el título
                OutlinedTextField(
                    value = title,
                    onValueChange = { title = it },
                    label = { Text("Título") },
                    modifier = Modifier.fillMaxWidth()
                )
                // Campo para la descripción
                OutlinedTextField(
                    value = description,
                    onValueChange = { description = it },
                    label = { Text("Descripción") },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        confirmButton = {
            // Botón para guardar, se habilita solo si el título no está vacío
            Button(
                onClick = { onConfirm(title, description) },
                enabled = title.isNotBlank()
            ) {
                Text("Guardar")
            }
        },
        dismissButton = {
            // Botón para cancelar la operación
            TextButton(onClick = onDismiss) {
                Text("Cancelar")
            }
        }
    )
}

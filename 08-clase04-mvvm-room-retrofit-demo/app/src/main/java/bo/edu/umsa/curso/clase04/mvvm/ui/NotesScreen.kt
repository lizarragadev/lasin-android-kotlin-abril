package bo.edu.umsa.curso.clase04.mvvm.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.Red
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun NotesScreen(
    viewModel: NotesViewModel,
    modifier: Modifier = Modifier
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    Column(
       modifier = Modifier
           .fillMaxSize()
           .padding(16.dp),
         verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        OutlinedTextField(
            value = state.query,
            onValueChange = viewModel::onQueryChange,
            label = { Text("Search") },
            modifier = Modifier.fillMaxWidth()
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text("Solo pendientes")
            Switch(
                checked = state.onlyPending,
                onCheckedChange = viewModel::onOnlyPendingChange
            )
        }
        Row(horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()) {
            Button(onClick = viewModel::onAddNoteClick) {
                Text("Agregar nota")
            }
            Button(onClick = viewModel::onSyncClick) {
                Text("Sync API")
            }
        }
        state.errorMessage?.let { error ->
            Text(text = error, color = Red)
        }
        if(state.isLoading){
            CircularProgressIndicator()
        }
    }
}
package tech.lizza.a07_clase_navegacion_y_material_design_3

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import tech.lizza.a07_clase_navegacion_y_material_design_3.ui.theme._07clasenavegacionymaterialdesign3Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            _07clasenavegacionymaterialdesign3Theme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                  
                }
            }
        }
    }
}

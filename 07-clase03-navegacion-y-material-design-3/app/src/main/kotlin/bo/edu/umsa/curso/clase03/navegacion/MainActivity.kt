package bo.edu.umsa.curso.clase03.navegacion

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import bo.edu.umsa.curso.clase03.navegacion.ui.theme.NavegacionMD3Theme

/**
 * Punto de partida vacío para la demo en vivo (navegación, pantallas y Activity extra se agregan en clase).
 */
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NavegacionMD3Theme {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(text = "Base: navegación MD3 (clase en vivo)")
                }
            }
        }
    }
}

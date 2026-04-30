package bo.edu.umsa.curso.clase03.navegacion.ui.components

import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow

/**
 * TopAppBar compacta: título con [Typography.titleMedium].
 *
 * - [respectStatusBarInsets] `false` (por defecto): no consume insets de estado; evita la
 *   “torre” lila en pantallas dentro de [MainActivity] con bottom bar.
 * - `true`: usa [TopAppBarDefaults.windowInsets] para que título y acciones queden **debajo**
 *   de la barra de notificaciones (útil en una Activity a pantalla completa como [AddDataActivity]).
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StandardTopAppBar(
    title: String,
    modifier: Modifier = Modifier,
    respectStatusBarInsets: Boolean = false,
    navigationIcon: @Composable () -> Unit = {},
    actions: @Composable RowScope.() -> Unit = {},
) {
    TopAppBar(
        modifier = modifier,
        windowInsets = if (respectStatusBarInsets) {
            TopAppBarDefaults.windowInsets
        } else {
            WindowInsets(0, 0, 0, 0)
        },
        title = {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
        },
        navigationIcon = navigationIcon,
        actions = actions,
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
        ),
    )
}

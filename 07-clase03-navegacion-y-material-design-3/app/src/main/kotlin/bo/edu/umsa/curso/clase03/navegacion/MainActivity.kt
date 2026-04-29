package bo.edu.umsa.curso.clase03.navegacion

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import bo.edu.umsa.curso.clase03.navegacion.navigation.NavGraph
import bo.edu.umsa.curso.clase03.navegacion.navigation.Screen
import bo.edu.umsa.curso.clase03.navegacion.ui.theme.NavegacionMD3Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NavegacionMD3Theme {
                MainApp()
            }
        }
    }
}

@Composable
fun MainApp() {
    val navController = rememberNavController()
    
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            BottomNavigationBar(navController = navController)
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
        ) {
            NavGraph(navController = navController)
        }
    }
}

private data class BottomNavItem(
    val navigateRoute: String,
    val label: String,
    val isSelected: (currentRoute: String?) -> Boolean,
    val iconContentDescription: String,
    val iconKey: BottomNavIcon,
)

private enum class BottomNavIcon { Home, Search, Profile, Settings }

@Composable
fun BottomNavigationBar(navController: androidx.navigation.NavHostController) {
    val profileDefault = Screen.Profile.createRoute("default")
    val items = listOf(
        BottomNavItem(
            navigateRoute = Screen.Home.route,
            label = "Inicio",
            isSelected = { it == Screen.Home.route },
            iconContentDescription = "Inicio",
            iconKey = BottomNavIcon.Home,
        ),
        BottomNavItem(
            navigateRoute = Screen.Search.route,
            label = "Búsqueda",
            isSelected = { it == Screen.Search.route },
            iconContentDescription = "Búsqueda",
            iconKey = BottomNavIcon.Search,
        ),
        BottomNavItem(
            navigateRoute = profileDefault,
            label = "Perfil",
            isSelected = { route -> route != null && route.startsWith("profile/") },
            iconContentDescription = "Perfil",
            iconKey = BottomNavIcon.Profile,
        ),
        BottomNavItem(
            navigateRoute = Screen.Settings.route,
            label = "Config",
            isSelected = { it == Screen.Settings.route },
            iconContentDescription = "Configuración",
            iconKey = BottomNavIcon.Settings,
        ),
    )

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    NavigationBar {
        items.forEach { item ->
            NavigationBarItem(
                icon = {
                    when (item.iconKey) {
                        BottomNavIcon.Home -> Icon(Icons.Filled.Home, contentDescription = item.iconContentDescription)
                        BottomNavIcon.Search -> Icon(Icons.Filled.Search, contentDescription = item.iconContentDescription)
                        BottomNavIcon.Profile -> Icon(Icons.Filled.Person, contentDescription = item.iconContentDescription)
                        BottomNavIcon.Settings -> Icon(Icons.Filled.Settings, contentDescription = item.iconContentDescription)
                    }
                },
                label = { Text(item.label) },
                selected = item.isSelected(currentRoute),
                onClick = {
                    navController.navigate(item.navigateRoute) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    }
}

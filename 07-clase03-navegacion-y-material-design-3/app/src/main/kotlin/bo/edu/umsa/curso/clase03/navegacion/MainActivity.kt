package bo.edu.umsa.curso.clase03.navegacion

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.navigation.NavDestination.Companion.hierarchy
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
    ) { _ ->
        NavGraph(navController = navController)
    }
}

@Composable
fun BottomNavigationBar(navController: androidx.navigation.NavHostController) {
    val items = listOf(
        Screen.Home,
        Screen.Search,
        Screen.Profile.createRoute("default"),
        Screen.Settings
    )
    
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    
    NavigationBar {
        items.forEach { screen ->
            val isSelected = currentDestination?.hierarchy?.any {
                it.route == when (screen) {
                    Screen.Home -> Screen.Home.route
                    Screen.Search -> Screen.Search.route
                    Screen.Settings -> Screen.Settings.route
                    is Screen.Profile -> "profile/{userId}"
                    else -> null
                }
            } ?: false
            
            NavigationBarItem(
                icon = {
                    when (screen) {
                        Screen.Home -> Icon(Icons.Filled.Home, contentDescription = "Inicio")
                        Screen.Search -> Icon(Icons.Filled.Search, contentDescription = "Búsqueda")
                        Screen.Settings -> Icon(Icons.Filled.Settings, contentDescription = "Configuración")
                        is Screen.Profile -> Icon(Icons.Filled.Person, contentDescription = "Perfil")
                        else -> {}
                    }
                },
                label = {
                    when (screen) {
                        Screen.Home -> Text("Inicio")
                        Screen.Search -> Text("Búsqueda")
                        Screen.Settings -> Text("Config")
                        is Screen.Profile -> Text("Perfil")
                        else -> {}
                    }
                },
                selected = isSelected,
                onClick = {
                    val route = when (screen) {
                        Screen.Home -> Screen.Home.route
                        Screen.Search -> Screen.Search.route
                        Screen.Settings -> Screen.Settings.route
                        is Screen.Profile -> screen
                        else -> return@NavigationBarItem
                    }
                    
                    if (route is Screen) {
                        navController.navigate(route.route) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                }
            )
        }
    }
}

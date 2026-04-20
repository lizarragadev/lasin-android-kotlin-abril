package bo.edu.umsa.curso.clase03.navegacion.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import bo.edu.umsa.curso.clase03.navegacion.screens.HomeScreen
import bo.edu.umsa.curso.clase03.navegacion.screens.SearchScreen
import bo.edu.umsa.curso.clase03.navegacion.screens.ProfileScreen
import bo.edu.umsa.curso.clase03.navegacion.screens.SettingsScreen

@Composable
fun NavGraph(navController: NavHostController) {
    NavHost(navController = navController, startDestination = Screen.Home.route) {
        
        // Home Screen
        composable(route = Screen.Home.route) {
            HomeScreen(
                onNavigateToProfile = { userId ->
                    navController.navigate(Screen.Profile.createRoute(userId))
                }
            )
        }
        
        // Search Screen
        composable(route = Screen.Search.route) {
            SearchScreen()
        }
        
        // Profile Screen con argumento
        composable(
            route = Screen.Profile.route,
            arguments = listOf(
                navArgument("userId") {
                    type = NavType.StringType
                    nullable = false
                }
            )
        ) { backStackEntry ->
            val userId = backStackEntry.arguments?.getString("userId") ?: ""
            ProfileScreen(userId = userId)
        }
        
        // Settings Screen
        composable(route = Screen.Settings.route) {
            SettingsScreen()
        }
    }
}

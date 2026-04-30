package ug.ac.ndejje.ndejjenest.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import ug.ac.ndejje.ndejjenest.view.*

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Screen.Splash.route) {
        composable(Screen.Splash.route) {
            SplashScreen(onSplashFinished = {
                navController.navigate(Screen.Onboarding.route) {
                    popUpTo(Screen.Splash.route) { inclusive = true }
                }
            })
        }
        composable(Screen.Onboarding.route) {
            OnboardingScreen(navController = navController)
        }
        composable(Screen.Login.route) {
            LoginScreen(navController = navController)
        }
        composable(Screen.Register.route) {
            RegisterScreen(navController = navController)
        }
        composable(Screen.Home.route) {
            HomeScreen(navController = navController)
        }
        composable(Screen.HostelDetails.route) { backStackEntry ->
            val hostelId = backStackEntry.arguments?.getString("hostelId")
            HostelDetailsScreen(navController = navController, hostelId = hostelId)
        }
        // ---- MODIFIED: Map route now passes optional hostelId (Map Screen - Feature 3) ----
        composable(
            route = Screen.Map.route,
            arguments = listOf(
                androidx.navigation.navArgument("hostelId") {
                    type = androidx.navigation.NavType.StringType
                    nullable = true
                    defaultValue = null
                }
            )
        ) { backStackEntry ->
            val hostelId = backStackEntry.arguments?.getString("hostelId")
            MapScreen(navController = navController, hostelId = hostelId)
        }
        // ---- END MODIFIED ----
        composable(Screen.SavedHostels.route) {
            SavedHostelsScreen(navController = navController)
        }
        composable(Screen.Profile.route) {
            ProfileScreen(navController = navController)
        }
        composable(Screen.EditProfile.route) {
            EditProfileScreen(navController = navController)
        }
    }
}

package ug.ac.ndejje.ndejjenest.navigation

sealed class Screen(val route: String) {
    object Splash : Screen("splash")
    object Onboarding : Screen("onboarding")
    object Login : Screen("login")
    object Register : Screen("register")
    object Home : Screen("home")
    object HostelDetails : Screen("hostel_details/{hostelId}") {
        fun createRoute(hostelId: String) = "hostel_details/$hostelId"
    }
    object Map : Screen("map")
    object SavedHostels : Screen("saved_hostels")
    object Profile : Screen("profile")
}

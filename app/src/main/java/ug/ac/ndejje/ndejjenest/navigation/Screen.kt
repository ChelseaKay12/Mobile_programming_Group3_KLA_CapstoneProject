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
    // ---- MODIFIED: Map route now accepts optional hostelId (Map Screen - Feature 3) ----
    object Map : Screen("map?hostelId={hostelId}") {
        fun createRoute(hostelId: String? = null): String {
            return if (hostelId != null) "map?hostelId=$hostelId" else "map"
        }
    }
    // ---- END MODIFIED ----
    object SavedHostels : Screen("saved_hostels")
    object Profile : Screen("profile")
    object EditProfile : Screen("edit_profile")
}

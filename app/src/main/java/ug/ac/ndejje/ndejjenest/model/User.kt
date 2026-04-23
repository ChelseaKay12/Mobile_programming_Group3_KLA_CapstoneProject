package ug.ac.ndejje.ndejjenest.model

/**
 * Represents a user in the NdejjeNest application.
 * This data class will be used when registering a new user.
 * In the future, it will be sent to Firebase or a local database.
 */
data class User(
    val fullName: String = "",
    val email: String = "",
    val phoneNumber: String = "",
    val role: String = "Student"  // Either "Student" or "Landlord"
)

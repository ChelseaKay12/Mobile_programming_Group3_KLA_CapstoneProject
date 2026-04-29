package ug.ac.ndejje.ndejjenest.model

data class Hostel(
    val id: String,
    val name: String,
    val location: String,
    val price: String,
    val rating: Double,
    val imageRes: Int? = null, // Placeholder for resource ID
    val imageUrl: String? = null
)

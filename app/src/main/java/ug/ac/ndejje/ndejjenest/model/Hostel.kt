package ug.ac.ndejje.ndejjenest.model

data class Hostel(
    val id: String,
    val name: String,
    val location: String,
    val price: String,
    val rating: Double,
    val description: String = "",
    val amenities: List<String> = emptyList(),
    val phoneNumber: String = "0756 123 456",
    val imageRes: Int? = null,
    val imageUrl: String? = null
)

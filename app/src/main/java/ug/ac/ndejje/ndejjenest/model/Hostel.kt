package ug.ac.ndejje.ndejjenest.model

data class Hostel(
    val id: String = "",
    val name: String = "",
    val location: String = "",
    val price: String = "",
    val rating: Double = 0.0,
    val description: String = "",
    val amenities: List<String> = emptyList(),
    val phoneNumber: String = "0756 123 456",
    val latitude: Double = 0.0,
    val longitude: Double = 0.0,
    val category: String = "Bombo",
    val imageRes: Int? = null,
    val imageUrl: String? = null
)

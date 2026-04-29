package ug.ac.ndejje.ndejjenest.model

class HostelRepository {
    fun getFeaturedHostels(): List<Hostel> {
        return listOf(
            Hostel(id = "1", name = "Grace Hostel", location = "Bombo", price = "UGX 250,000", rating = 4.5),
            Hostel(id = "2", name = "Peace Apartments", location = "Luwero", price = "UGX 300,000", rating = 4.0),
            Hostel(id = "3", name = "Skyline Hostel", location = "Kampala", price = "UGX 450,000", rating = 4.8),
            Hostel(id = "5", name = "Elite Nest", location = "Bombo", price = "UGX 280,000", rating = 4.2),
            Hostel(id = "6", name = "Sunshine Plaza", location = "Luwero", price = "UGX 320,000", rating = 4.3)
        )
    }

    fun getRecommendedHostels(): List<Hostel> {
        return listOf(
            Hostel(id = "4", name = "Bright Future Hostel", location = "Kampala", price = "UGX 350,000", rating = 4.7),
            Hostel(id = "7", name = "Royal Palms", location = "Kampala", price = "UGX 500,000", rating = 4.9),
            Hostel(id = "8", name = "Green Valley", location = "Bombo", price = "UGX 220,000", rating = 4.1),
            Hostel(id = "9", name = "Serene Stay", location = "Luwero", price = "UGX 275,000", rating = 4.4),
            Hostel(id = "10", name = "City Center Hostel", location = "Kampala", price = "UGX 400,000", rating = 4.6)
        )
    }
}

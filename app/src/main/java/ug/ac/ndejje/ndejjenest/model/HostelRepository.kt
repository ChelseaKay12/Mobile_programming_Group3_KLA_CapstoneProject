package ug.ac.ndejje.ndejjenest.model

class HostelRepository {
    fun getFeaturedHostels(): List<Hostel> {
        return listOf(
            Hostel(
                id = "1",
                name = "Grace Hostel",
                location = "Bombo",
                price = "UGX 250,000",
                rating = 4.5
            ),
            Hostel(
                id = "2",
                name = "Peace Apartments",
                location = "Luwero",
                price = "UGX 300,000",
                rating = 4.0
            ),
            Hostel(
                id = "3",
                name = "Skyline Hostel",
                location = "Kampala",
                price = "UGX 450,000",
                rating = 4.8
            )
        )
    }

    fun getRecommendedHostels(): List<Hostel> {
        return listOf(
            Hostel(
                id = "4",
                name = "Bright Future Hostel",
                location = "Kampala",
                price = "UGX 350,000",
                rating = 4.7
            )
        )
    }
}

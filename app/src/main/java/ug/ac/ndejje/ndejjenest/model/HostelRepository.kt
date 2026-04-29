package ug.ac.ndejje.ndejjenest.model

class HostelRepository {
    fun getFeaturedHostels(): List<Hostel> {
        return listOf(
            Hostel(
                id = "1",
                name = "Grace Hostel",
                location = "Bombo",
                price = "UGX 250,000",
                rating = 4.5,
                description = "Grace Hostel offers a serene environment for students, featuring spacious rooms and 24/7 security. Located just 5 minutes from the main campus.",
                amenities = listOf("WiFi", "Water", "CCTV", "Electricity"),
                phoneNumber = "0756 123 456"
            ),
            Hostel(
                id = "2",
                name = "Peace Apartments",
                location = "Luwero",
                price = "UGX 300,000",
                rating = 4.0,
                description = "Modern apartments with a focus on peace and quiet. Ideal for serious students who need a productive study space.",
                amenities = listOf("Quiet Zone", "WiFi", "Gated", "Water"),
                phoneNumber = "0701 987 654"
            ),
            Hostel(
                id = "3",
                name = "Skyline Hostel",
                location = "Kampala",
                price = "UGX 450,000",
                rating = 4.8,
                description = "Premium hostel with a great view of the city. High-speed internet and modern furnishing included.",
                amenities = listOf("High-Speed WiFi", "Gym", "Balcony", "Security"),
                phoneNumber = "0782 555 111"
            ),
            Hostel(
                id = "5",
                name = "Elite Nest",
                location = "Bombo",
                price = "UGX 280,000",
                rating = 4.2,
                description = "A comfortable nest for elite students. Close to essential services and transport links.",
                amenities = listOf("WiFi", "Electricity", "Common Area", "Water"),
                phoneNumber = "0773 444 222"
            ),
            Hostel(
                id = "6",
                name = "Sunshine Plaza",
                location = "Luwero",
                price = "UGX 320,000",
                rating = 4.3,
                description = "Bright and airy rooms with plenty of natural light. Excellent community atmosphere.",
                amenities = listOf("Natural Light", "Social Space", "CCTV", "WiFi"),
                phoneNumber = "0759 333 999"
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
                rating = 4.7,
                description = "Invest in your future with a stay at Bright Future. Modern facilities and a supportive community.",
                amenities = listOf("Study Hall", "WiFi", "Laundry", "24/7 Guard"),
                phoneNumber = "0704 111 222"
            ),
            Hostel(
                id = "7",
                name = "Royal Palms",
                location = "Kampala",
                price = "UGX 500,000",
                rating = 4.9,
                description = "Luxury living for the discerning student. Every room is self-contained with premium fittings.",
                amenities = listOf("AC", "Self-Contained", "Pool", "WiFi"),
                phoneNumber = "0785 666 777"
            ),
            Hostel(
                id = "8",
                name = "Green Valley",
                location = "Bombo",
                price = "UGX 220,000",
                rating = 4.1,
                description = "Affordable and eco-friendly housing. Surrounded by greenery and fresh air.",
                amenities = listOf("Garden", "Water Tank", "Security", "WiFi"),
                phoneNumber = "0771 888 999"
            ),
            Hostel(
                id = "9",
                name = "Serene Stay",
                location = "Luwero",
                price = "UGX 275,000",
                rating = 4.4,
                description = "Live in serenity. Our hostel provides the ultimate calm for a balanced student life.",
                amenities = listOf("WiFi", "Constant Water", "Electricity", "Gated"),
                phoneNumber = "0702 444 555"
            ),
            Hostel(
                id = "10",
                name = "City Center Hostel",
                location = "Kampala",
                price = "UGX 400,000",
                rating = 4.6,
                description = "Located in the heart of the city. Easy access to shopping centers and public transport.",
                amenities = listOf("City Access", "High Security", "WiFi", "Backup Power"),
                phoneNumber = "0758 777 888"
            )
        )
    }
}

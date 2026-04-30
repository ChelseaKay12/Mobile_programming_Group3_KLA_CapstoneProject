package ug.ac.ndejje.ndejjenest.util

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import ug.ac.ndejje.ndejjenest.model.Hostel
import ug.ac.ndejje.ndejjenest.model.HostelRepository

class DataMigrationManager {

    private val firestore = FirebaseFirestore.getInstance()
    private val repository = HostelRepository()

    /**
     * Uploads the hardcoded hostels from HostelRepository to Firestore.
     * Includes high-quality placeholder URLs for images.
     */
    fun uploadMockDataToFirestore() {
        val allHostels = repository.getFeaturedHostels() + repository.getRecommendedHostels()
        
        // Map of ID to high-quality Unsplash architecture/room images
        val imageUrls = mapOf(
            "1" to "https://images.unsplash.com/photo-1555854817-5b2738f751a7?q=80&w=800", // Grace
            "2" to "https://images.unsplash.com/photo-1522771739844-6a9f6d5f14af?q=80&w=800", // Peace
            "3" to "https://images.unsplash.com/photo-1595526114035-0d45ed16cfbf?q=80&w=800", // Skyline
            "4" to "https://images.unsplash.com/photo-1505691938895-1758d7eaa511?q=80&w=800", // Bright Future
            "5" to "https://images.unsplash.com/photo-1512918728675-ed5a9ecdebfd?q=80&w=800", // Elite Nest
            "6" to "https://images.unsplash.com/photo-1600585154340-be6161a56a0c?q=80&w=800", // Sunshine
            "7" to "https://images.unsplash.com/photo-1613490493576-7fde63acd811?q=80&w=800", // Royal Palms
            "8" to "https://images.unsplash.com/photo-1564013799919-ab600027ffc6?q=80&w=800", // Green Valley
            "9" to "https://images.unsplash.com/photo-1582268611958-ebfd161ef9cf?q=80&w=800", // Serene Stay
            "10" to "https://images.unsplash.com/photo-1574362848149-11496d93a7c7?q=80&w=800" // City Center
        )

        val batch = firestore.batch()

        allHostels.forEach { hostel ->
            val docRef = firestore.collection("hostels").document(hostel.id)
            
            // Create a map with the image URL included
            val hostelData = mapOf(
                "id" to hostel.id,
                "name" to hostel.name,
                "location" to hostel.location,
                "price" to hostel.price,
                "rating" to hostel.rating,
                "description" to hostel.description,
                "amenities" to hostel.amenities,
                "phoneNumber" to hostel.phoneNumber,
                "latitude" to hostel.latitude,
                "longitude" to hostel.longitude,
                "imageUrl" to (imageUrls[hostel.id] ?: "")
            )
            
            batch.set(docRef, hostelData)
        }

        batch.commit()
            .addOnSuccessListener {
                Log.d("Migration", "Successfully uploaded all hostels to Firestore!")
            }
            .addOnFailureListener { e ->
                Log.e("Migration", "Error uploading hostels", e)
            }
    }
}

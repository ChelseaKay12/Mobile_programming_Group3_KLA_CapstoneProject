package ug.ac.ndejje.ndejjenest.model

import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await

class HostelRepository {

    private val firestore = FirebaseFirestore.getInstance()
    private val hostelsCollection = firestore.collection("hostels")

    /**
     * Fetches all hostels from Firestore as a real-time Flow.
     * This allows the UI to update automatically if the database changes.
     */
    fun getAllHostels(): Flow<List<Hostel>> = callbackFlow {
        val subscription = hostelsCollection.addSnapshotListener { snapshot, error ->
            if (error != null) {
                close(error)
                return@addSnapshotListener
            }
            
            val hostels = snapshot?.documents?.mapNotNull { doc ->
                doc.toObject(Hostel::class.java)?.copy(id = doc.id)
            } ?: emptyList()
            
            trySend(hostels)
        }
        awaitClose { subscription.remove() }
    }

    /**
     * Fetches only "Featured" hostels (those with a rating >= 4.5).
     */
    fun getFeaturedHostelsLive(): Flow<List<Hostel>> = callbackFlow {
        val query = hostelsCollection.whereGreaterThanOrEqualTo("rating", 4.5)
        val subscription = query.addSnapshotListener { snapshot, error ->
            if (error != null) {
                close(error)
                return@addSnapshotListener
            }
            
            val hostels = snapshot?.documents?.mapNotNull { doc ->
                doc.toObject(Hostel::class.java)?.copy(id = doc.id)
            } ?: emptyList()
            
            trySend(hostels)
        }
        awaitClose { subscription.remove() }
    }

    /**
     * Fetches a single hostel by its ID from Firestore.
     */
    suspend fun getHostelById(id: String): Hostel? {
        return try {
            val document = hostelsCollection.document(id).get().await()
            document.toObject(Hostel::class.java)?.copy(id = document.id)
        } catch (e: Exception) {
            null
        }
    }

    /**
     * Toggles the saved status of a hostel for a specific user using a Firestore Transaction.
     */
    suspend fun toggleSavedHostel(userId: String, hostelId: String) {
        val userRef = firestore.collection("users").document(userId)
        
        try {
            firestore.runTransaction { transaction ->
                val snapshot = transaction.get(userRef)
                val currentSaved = snapshot.get("savedHostelIds") as? List<String> ?: emptyList()
                
                val newSaved = if (currentSaved.contains(hostelId)) {
                    currentSaved - hostelId
                } else {
                    currentSaved + hostelId
                }
                
                transaction.update(userRef, "savedHostelIds", newSaved)
            }.await()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    /**
     * Fetches only the hostels that match the provided list of IDs.
     * This is used for the Saved Hostels screen.
     */
    fun getSavedHostels(hostelIds: List<String>): Flow<List<Hostel>> = callbackFlow {
        if (hostelIds.isEmpty()) {
            trySend(emptyList())
            val emptySubscription = object : com.google.firebase.firestore.ListenerRegistration {
                override fun remove() {}
            }
            awaitClose { emptySubscription.remove() }
            return@callbackFlow
        }
        
        // Firestore 'whereIn' supports up to 30 IDs
        val query = hostelsCollection.whereIn(com.google.firebase.firestore.FieldPath.documentId(), hostelIds)
        val subscription = query.addSnapshotListener { snapshot, error ->
            if (error != null) {
                close(error)
                return@addSnapshotListener
            }
            
            val hostels = snapshot?.documents?.mapNotNull { doc ->
                doc.toObject(Hostel::class.java)?.copy(id = doc.id)
            } ?: emptyList()
            
            trySend(hostels)
        }
        awaitClose { subscription.remove() }
    }

    // --- LEGACY MOCK DATA (Keeping for reference during transition) ---
    // These will be removed once ViewModels are fully updated.
    fun getFeaturedHostels(): List<Hostel> = emptyList()
    fun getRecommendedHostels(): List<Hostel> = emptyList()
}

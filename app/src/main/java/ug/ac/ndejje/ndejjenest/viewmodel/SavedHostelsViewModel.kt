package ug.ac.ndejje.ndejjenest.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import ug.ac.ndejje.ndejjenest.model.Hostel
import ug.ac.ndejje.ndejjenest.model.HostelRepository

/**
 * ViewModel for the Saved Hostels screen.
 * It observes the user's bookmarked hostel IDs in Firestore and fetches the corresponding hostel details.
 */
class SavedHostelsViewModel : ViewModel() {

    private val auth = FirebaseAuth.getInstance()
    private val firestore = FirebaseFirestore.getInstance()
    private val repository = HostelRepository()

    // State for the list of saved hostels
    private val _savedHostels = MutableStateFlow<List<Hostel>>(emptyList())
    val savedHostels: StateFlow<List<Hostel>> = _savedHostels.asStateFlow()

    // Loading state
    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    init {
        observeSavedHostelIds()
    }

    /**
     * First, we observe the 'savedHostelIds' field in the user's Firestore document.
     * When that list changes, we trigger a fetch for the actual Hostel objects.
     */
    private fun observeSavedHostelIds() {
        val uid = auth.currentUser?.uid ?: return

        firestore.collection("users").document(uid)
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    _isLoading.value = false
                    return@addSnapshotListener
                }

                val hostelIds = snapshot?.get("savedHostelIds") as? List<String> ?: emptyList()
                
                if (hostelIds.isEmpty()) {
                    _savedHostels.value = emptyList()
                    _isLoading.value = false
                } else {
                    fetchHostelDetails(hostelIds)
                }
            }
    }

    /**
     * Fetches the full Hostel objects based on the IDs provided.
     */
    private fun fetchHostelDetails(hostelIds: List<String>) {
        viewModelScope.launch {
            repository.getSavedHostels(hostelIds)
                .collect { hostels ->
                    _savedHostels.value = hostels
                    _isLoading.value = false
                }
        }
    }

    /**
     * Toggles the saved status. Used to remove a hostel from the list.
     */
    fun toggleSave(hostelId: String) {
        val uid = auth.currentUser?.uid ?: return
        viewModelScope.launch {
            repository.toggleSavedHostel(uid, hostelId)
        }
    }
}

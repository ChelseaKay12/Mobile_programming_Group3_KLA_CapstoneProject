package ug.ac.ndejje.ndejjenest.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ug.ac.ndejje.ndejjenest.model.Hostel
import ug.ac.ndejje.ndejjenest.model.HostelRepository

class HostelDetailsViewModel : ViewModel() {
    private val repository = HostelRepository()

    private val _hostel = MutableStateFlow<Hostel?>(null)
    val hostel = _hostel.asStateFlow()

    private val _isSaved = MutableStateFlow(false)
    val isSaved = _isSaved.asStateFlow()

    fun getHostel(id: String) {
        viewModelScope.launch {
            // Fetch the specific hostel from Firestore by ID
            val result = repository.getHostelById(id)
            _hostel.value = result
            observeSavedStatus(id)
        }
    }

    private fun observeSavedStatus(hostelId: String) {
        val uid = com.google.firebase.auth.FirebaseAuth.getInstance().currentUser?.uid ?: return
        com.google.firebase.firestore.FirebaseFirestore.getInstance()
            .collection("users").document(uid)
            .addSnapshotListener { snapshot, _ ->
                val savedIds = snapshot?.get("savedHostelIds") as? List<String> ?: emptyList()
                _isSaved.value = savedIds.contains(hostelId)
            }
    }

    fun toggleSave() {
        val uid = com.google.firebase.auth.FirebaseAuth.getInstance().currentUser?.uid ?: return
        val hostelId = _hostel.value?.id ?: return
        viewModelScope.launch {
            repository.toggleSavedHostel(uid, hostelId)
        }
    }
}

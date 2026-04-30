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

    fun getHostel(id: String) {
        viewModelScope.launch {
            // Fetch the specific hostel from Firestore by ID
            val result = repository.getHostelById(id)
            _hostel.value = result
        }
    }
}

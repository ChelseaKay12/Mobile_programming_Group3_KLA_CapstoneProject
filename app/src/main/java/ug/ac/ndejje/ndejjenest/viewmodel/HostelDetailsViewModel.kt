package ug.ac.ndejje.ndejjenest.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import ug.ac.ndejje.ndejjenest.model.Hostel
import ug.ac.ndejje.ndejjenest.model.HostelRepository

class HostelDetailsViewModel : ViewModel() {
    private val repository = HostelRepository()

    private val _hostel = MutableStateFlow<Hostel?>(null)
    val hostel = _hostel.asStateFlow()

    fun getHostel(id: String) {
        val allHostels = repository.getFeaturedHostels() + repository.getRecommendedHostels()
        _hostel.value = allHostels.find { it.id == id }
    }
}

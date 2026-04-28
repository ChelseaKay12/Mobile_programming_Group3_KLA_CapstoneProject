package ug.ac.ndejje.ndejjenest.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

import ug.ac.ndejje.ndejjenest.model.Hostel
import ug.ac.ndejje.ndejjenest.model.HostelRepository

class HomeViewModel : ViewModel() {
    private val repository = HostelRepository()

    private val _searchQuery = MutableStateFlow("")
    val searchQuery = _searchQuery.asStateFlow()

    private val _featuredHostels = MutableStateFlow<List<Hostel>>(emptyList())
    val featuredHostels = _featuredHostels.asStateFlow()

    init {
        loadData()
    }

    private fun loadData() {
        _featuredHostels.value = repository.getFeaturedHostels()
    }

    fun onSearchQueryChange(query: String) {
        _searchQuery.value = query
    }
}

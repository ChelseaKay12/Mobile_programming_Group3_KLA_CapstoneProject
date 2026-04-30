package ug.ac.ndejje.ndejjenest.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch

import ug.ac.ndejje.ndejjenest.model.Hostel
import ug.ac.ndejje.ndejjenest.model.HostelRepository

class HomeViewModel : ViewModel() {
    private val repository = HostelRepository()

    private val _searchQuery = MutableStateFlow("")
    val searchQuery = _searchQuery.asStateFlow()

    private val _selectedCategory = MutableStateFlow("All")
    val selectedCategory = _selectedCategory.asStateFlow()

    // Internal full list of hostels from Firestore
    private val _allHostels = MutableStateFlow<List<Hostel>>(emptyList())

    // UI State: Filtered lists
    private val _featuredHostels = MutableStateFlow<List<Hostel>>(emptyList())
    val featuredHostels = _featuredHostels.asStateFlow()

    private val _recommendedHostels = MutableStateFlow<List<Hostel>>(emptyList())
    val recommendedHostels = _recommendedHostels.asStateFlow()

    init {
        observeHostels()
    }

    private fun observeHostels() {
        viewModelScope.launch {
            // Observe the live flow from the repository
            repository.getAllHostels().collect { hostels ->
                _allHostels.value = hostels
                filterHostels(hostels)
            }
        }
    }

    fun onSearchQueryChange(query: String) {
        _searchQuery.value = query
        filterHostels(_allHostels.value)
    }

    fun onCategorySelected(category: String) {
        _selectedCategory.value = category
        filterHostels(_allHostels.value)
    }

    private fun filterHostels(hostels: List<Hostel>) {
        val category = _selectedCategory.value
        val query = _searchQuery.value

        // Featured: High ratings (>= 4.5) + Filters
        _featuredHostels.value = hostels.filter {
            it.rating >= 4.5 &&
            (category == "All" || it.category == category) &&
            (query.isEmpty() || it.name.contains(query, ignoreCase = true) || it.location.contains(query, ignoreCase = true))
        }

        // Recommended: All others + Filters
        _recommendedHostels.value = hostels.filter {
            it.rating < 4.5 &&
            (category == "All" || it.category == category) &&
            (query.isEmpty() || it.name.contains(query, ignoreCase = true) || it.location.contains(query, ignoreCase = true))
        }
    }
}

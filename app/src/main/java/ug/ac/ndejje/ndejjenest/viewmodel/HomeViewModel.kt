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

    private val _selectedCategory = MutableStateFlow("All")
    val selectedCategory = _selectedCategory.asStateFlow()

    private val _featuredHostels = MutableStateFlow<List<Hostel>>(emptyList())
    val featuredHostels = _featuredHostels.asStateFlow()

    private val _recommendedHostels = MutableStateFlow<List<Hostel>>(emptyList())
    val recommendedHostels = _recommendedHostels.asStateFlow()

    private var allFeaturedHostels = emptyList<Hostel>()
    private var allRecommendedHostels = emptyList<Hostel>()

    init {
        loadData()
    }

    private fun loadData() {
        allFeaturedHostels = repository.getFeaturedHostels()
        allRecommendedHostels = repository.getRecommendedHostels()
        filterHostels()
    }

    fun onSearchQueryChange(query: String) {
        _searchQuery.value = query
        filterHostels()
    }

    fun onCategorySelected(category: String) {
        _selectedCategory.value = category
        filterHostels()
    }

    private fun filterHostels() {
        val category = _selectedCategory.value
        val query = _searchQuery.value

        _featuredHostels.value = allFeaturedHostels.filter {
            (category == "All" || it.location == category) &&
            (query.isEmpty() || it.name.contains(query, ignoreCase = true) || it.location.contains(query, ignoreCase = true))
        }

        _recommendedHostels.value = allRecommendedHostels.filter {
            (category == "All" || it.location == category) &&
            (query.isEmpty() || it.name.contains(query, ignoreCase = true) || it.location.contains(query, ignoreCase = true))
        }
    }
}

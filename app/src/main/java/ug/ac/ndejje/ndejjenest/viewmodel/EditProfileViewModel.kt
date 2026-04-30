package ug.ac.ndejje.ndejjenest.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class EditProfileViewModel : ViewModel() {

    private val auth = FirebaseAuth.getInstance()
    private val firestore = FirebaseFirestore.getInstance()

    // Form States
    private val _fullName = MutableStateFlow("")
    val fullName = _fullName.asStateFlow()

    private val _phoneNumber = MutableStateFlow("")
    val phoneNumber = _phoneNumber.asStateFlow()

    private val _email = MutableStateFlow("")
    val email = _email.asStateFlow()

    // UI States
    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    private val _isSuccess = MutableStateFlow(false)
    val isSuccess = _isSuccess.asStateFlow()

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage = _errorMessage.asStateFlow()

    init {
        fetchUserData()
    }

    private fun fetchUserData() {
        val uid = auth.currentUser?.uid ?: return
        _isLoading.value = true
        
        firestore.collection("users").document(uid).get()
            .addOnSuccessListener { document ->
                if (document.exists()) {
                    _fullName.value = document.getString("fullName") ?: ""
                    _phoneNumber.value = document.getString("phoneNumber") ?: ""
                    _email.value = document.getString("email") ?: auth.currentUser?.email ?: ""
                }
                _isLoading.value = false
            }
            .addOnFailureListener {
                _isLoading.value = false
                _errorMessage.value = "Failed to load profile data"
            }
    }

    fun onFullNameChanged(newName: String) {
        _fullName.value = newName
    }

    fun onPhoneNumberChanged(newPhone: String) {
        _phoneNumber.value = newPhone
    }

    fun updateProfile() {
        val uid = auth.currentUser?.uid ?: return
        
        if (_fullName.value.isBlank()) {
            _errorMessage.value = "Name cannot be empty"
            return
        }

        _isLoading.value = true
        val updates = mapOf(
            "fullName" to _fullName.value,
            "phoneNumber" to _phoneNumber.value
        )

        firestore.collection("users").document(uid).update(updates)
            .addOnSuccessListener {
                _isLoading.value = false
                _isSuccess.value = true
            }
            .addOnFailureListener {
                _isLoading.value = false
                _errorMessage.value = "Failed to update profile. Please try again."
            }
    }

    fun clearError() {
        _errorMessage.value = null
    }
    
    fun resetSuccess() {
        _isSuccess.value = false
    }
}

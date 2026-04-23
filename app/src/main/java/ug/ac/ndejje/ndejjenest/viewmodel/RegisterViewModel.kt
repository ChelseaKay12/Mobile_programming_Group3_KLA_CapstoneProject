package ug.ac.ndejje.ndejjenest.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

/**
 * ViewModel for the Register screen.
 * This class handles the "Logic" and stores the "Data" for the UI.
 * More state fields will be added as we implement each feature.
 */
class RegisterViewModel : ViewModel() {

    // --- State (Data) ---
    private val _fullName = MutableStateFlow("")
    val fullName: StateFlow<String> = _fullName.asStateFlow()

    private val _email = MutableStateFlow("")
    val email: StateFlow<String> = _email.asStateFlow()

    private val _phoneNumber = MutableStateFlow("")
    val phoneNumber: StateFlow<String> = _phoneNumber.asStateFlow()

    // --- Actions (Logic) ---

    fun onFullNameChanged(newName: String) {
        _fullName.value = newName
    }

    fun onEmailChanged(newEmail: String) {
        _email.value = newEmail
    }

    fun onPhoneNumberChanged(newPhone: String) {
        _phoneNumber.value = newPhone
    }

    fun onRegisterClicked() {
        // Here we will eventually add logic to create a new user account
        println("Register clicked")
    }

    fun onLoginClicked() {
        // Placeholder for navigating back to login
        println("Go to Login clicked")
    }
}

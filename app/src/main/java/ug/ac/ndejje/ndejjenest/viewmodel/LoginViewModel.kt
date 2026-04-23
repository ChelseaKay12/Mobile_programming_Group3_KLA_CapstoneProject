package ug.ac.ndejje.ndejjenest.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

/**
 * ViewModel for the Login screen.
 * This class handles the "Logic" and stores the "Data" for the UI.
 */
class LoginViewModel : ViewModel() {

    // --- State (Data) ---
    
    // We use MutableStateFlow to hold the text the user types
    private val _email = MutableStateFlow("")
    val email: StateFlow<String> = _email.asStateFlow()

    private val _password = MutableStateFlow("")
    val password: StateFlow<String> = _password.asStateFlow()

    private val _isPasswordVisible = MutableStateFlow(false)
    val isPasswordVisible: StateFlow<Boolean> = _isPasswordVisible.asStateFlow()

    // --- Actions (Logic) ---

    fun onEmailChanged(newEmail: String) {
        _email.value = newEmail
    }

    fun onPasswordChanged(newPassword: String) {
        _password.value = newPassword
    }

    fun togglePasswordVisibility() {
        _isPasswordVisible.value = !_isPasswordVisible.value
    }

    fun onForgotPasswordClicked() {
        // Placeholder for navigation to forgot password screen
        println("Forgot password clicked")
    }

    fun onLoginClicked() {
        // Here we will eventually add logic to talk to a server
        println("Login clicked with Email: ${_email.value}")
    }
}

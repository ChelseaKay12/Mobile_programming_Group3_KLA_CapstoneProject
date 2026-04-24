package ug.ac.ndejje.ndejjenest.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import ug.ac.ndejje.ndejjenest.model.AuthRepository

/**
 * ViewModel for the Login screen.
 * This class handles the "Logic" and stores the "Data" for the UI.
 */
class LoginViewModel : ViewModel() {

    private val authRepository = AuthRepository()

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

    fun onRegisterClicked() {
        // Placeholder for navigation to registration screen
        println("Register clicked")
    }

    // --- Firebase Feedback States ---
    // These states tell the UI what is happening behind the scenes so it can show spinners or errors

    // Becomes true when the app is talking to Firebase
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    // Holds an error message (like "Invalid email") if something goes wrong
    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage.asStateFlow()

    // Becomes true only when Firebase successfully logs the user in
    private val _isSuccess = MutableStateFlow(false)
    val isSuccess: StateFlow<Boolean> = _isSuccess.asStateFlow()

    fun onLoginClicked() {
        // Step 1: Basic Validation - ensure the fields aren't empty
        if (_email.value.isBlank() || _password.value.isBlank()) {
            _errorMessage.value = "Please enter both email and password."
            return
        }

        // Step 2: Start Loading - clear old errors and tell UI to show a spinner
        _isLoading.value = true
        _errorMessage.value = null

        // Step 3: Send the login request to Firebase via our AuthRepository
        authRepository.loginUser(_email.value, _password.value) { success, message ->
            // Stop loading when Firebase responds
            _isLoading.value = false
            
            if (success) {
                // If it worked, tell the UI so it can navigate to the Home screen
                _isSuccess.value = true
            } else {
                // If it failed, show the error message from Firebase
                _errorMessage.value = message
            }
        }
    }

    fun clearErrorMessage() {
        _errorMessage.value = null
    }
}

package ug.ac.ndejje.ndejjenest.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import ug.ac.ndejje.ndejjenest.model.AuthRepository
import ug.ac.ndejje.ndejjenest.model.User

/**
 * ViewModel for the Register screen.
 * This class handles the "Logic" and stores the "Data" for the UI.
 * More state fields will be added as we implement each feature.
 */
class RegisterViewModel : ViewModel() {

    private val authRepository = AuthRepository()

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

    private val _password = MutableStateFlow("")
    val password: StateFlow<String> = _password.asStateFlow()

    private val _confirmPassword = MutableStateFlow("")
    val confirmPassword: StateFlow<String> = _confirmPassword.asStateFlow()

    private val _isPasswordVisible = MutableStateFlow(false)
    val isPasswordVisible: StateFlow<Boolean> = _isPasswordVisible.asStateFlow()

    private val _isConfirmPasswordVisible = MutableStateFlow(false)
    val isConfirmPasswordVisible: StateFlow<Boolean> = _isConfirmPasswordVisible.asStateFlow()

    fun onPasswordChanged(newPassword: String) {
        _password.value = newPassword
    }

    fun onConfirmPasswordChanged(newConfirmPassword: String) {
        _confirmPassword.value = newConfirmPassword
    }

    fun togglePasswordVisibility() {
        _isPasswordVisible.value = !_isPasswordVisible.value
    }

    fun toggleConfirmPasswordVisibility() {
        _isConfirmPasswordVisible.value = !_isConfirmPasswordVisible.value
    }

    private val _isTermsAccepted = MutableStateFlow(false)
    val isTermsAccepted: StateFlow<Boolean> = _isTermsAccepted.asStateFlow()

    fun onTermsCheckedChanged(isChecked: Boolean) {
        _isTermsAccepted.value = isChecked
    }

    // --- Firebase Feedback States ---
    // These states tell the UI what is happening behind the scenes so it can show spinners or errors

    // Becomes true when the app is talking to Firebase
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    // Holds an error message (like "Passwords don't match") if something goes wrong
    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage.asStateFlow()

    // Becomes true only when Firebase successfully creates the account
    private val _isSuccess = MutableStateFlow(false)
    val isSuccess: StateFlow<Boolean> = _isSuccess.asStateFlow()

    fun onRegisterClicked() {
        // Step 1: Basic Validation - make sure the passwords match and are long enough
        if (_password.value != _confirmPassword.value) {
            _errorMessage.value = "Passwords do not match."
            return
        }
        
        if (_password.value.length < 6) {
            _errorMessage.value = "Password must be at least 6 characters."
            return
        }

        // Step 2: Start Loading - clear old errors and tell UI to show a spinner
        _isLoading.value = true
        _errorMessage.value = null

        // Step 3: Package the data into our User model
        val user = User(
            fullName = _fullName.value,
            email = _email.value,
            phoneNumber = _phoneNumber.value
        )

        // Step 4: Send the data to Firebase via our AuthRepository
        authRepository.registerUser(user, _password.value) { success, message ->
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

    fun onLoginClicked() {
        // Placeholder for navigating back to login
        println("Go to Login clicked")
    }
}

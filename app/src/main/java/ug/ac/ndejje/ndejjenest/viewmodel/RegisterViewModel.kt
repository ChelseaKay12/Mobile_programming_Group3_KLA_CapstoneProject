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
    // These will be filled in as we add each feature

    // --- Actions (Logic) ---

    fun onRegisterClicked() {
        // Here we will eventually add logic to create a new user account
        println("Register clicked")
    }

    fun onLoginClicked() {
        // Placeholder for navigating back to login
        println("Go to Login clicked")
    }
}

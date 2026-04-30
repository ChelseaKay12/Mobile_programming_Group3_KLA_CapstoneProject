package ug.ac.ndejje.ndejjenest.viewmodel

// ---- NEW: ProfileViewModel to fetch user data from Firebase (Profile Screen) ----

import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class ProfileViewModel : ViewModel() {

    private val auth = FirebaseAuth.getInstance()
    private val firestore = FirebaseFirestore.getInstance()

    // User profile state
    private val _fullName = MutableStateFlow("Loading...")
    val fullName = _fullName.asStateFlow()

    private val _email = MutableStateFlow("Loading...")
    val email = _email.asStateFlow()

    private val _phoneNumber = MutableStateFlow("")
    val phoneNumber = _phoneNumber.asStateFlow()

    init {
        fetchUserProfile()
    }

    /**
     * Fetches the user's profile data from Firestore using their UID.
     * The data was saved during registration in AuthRepository.registerUser().
     */
    private fun fetchUserProfile() {
        val uid = auth.currentUser?.uid

        if (uid != null) {
            // Read from Firestore "users" collection where we saved during registration
            firestore.collection("users").document(uid)
                .get()
                .addOnSuccessListener { document ->
                    if (document.exists()) {
                        // These field names match the User data class used in registration
                        _fullName.value = document.getString("fullName") ?: "Student"
                        _email.value = document.getString("email") ?: auth.currentUser?.email ?: ""
                        _phoneNumber.value = document.getString("phoneNumber") ?: ""
                    } else {
                        // Document doesn't exist, fall back to Auth data
                        _fullName.value = auth.currentUser?.displayName ?: "Student"
                        _email.value = auth.currentUser?.email ?: ""
                    }
                }
                .addOnFailureListener {
                    // Firestore failed, fall back to Auth data
                    _fullName.value = auth.currentUser?.displayName ?: "Student"
                    _email.value = auth.currentUser?.email ?: ""
                }
        } else {
            _fullName.value = "Guest"
            _email.value = "Not logged in"
        }
    }

    /**
     * Signs out the user from Firebase Auth.
     */
    fun signOut() {
        auth.signOut()
    }
}

// ---- END NEW ----

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

    private val _savedCount = MutableStateFlow(0)
    val savedCount = _savedCount.asStateFlow()

    private var userListener: com.google.firebase.firestore.ListenerRegistration? = null

    init {
        fetchUserProfile()
    }

    private fun fetchUserProfile() {
        val uid = auth.currentUser?.uid ?: return

        userListener = firestore.collection("users").document(uid)
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    _fullName.value = auth.currentUser?.displayName ?: "Student"
                    _email.value = auth.currentUser?.email ?: ""
                    return@addSnapshotListener
                }

                if (snapshot != null && snapshot.exists()) {
                    _fullName.value = snapshot.getString("fullName") ?: "Student"
                    _email.value = snapshot.getString("email") ?: auth.currentUser?.email ?: ""
                    _phoneNumber.value = snapshot.getString("phoneNumber") ?: ""
                    
                    val savedIds = snapshot.get("savedHostelIds") as? List<*>
                    _savedCount.value = savedIds?.size ?: 0
                }
            }
    }

    override fun onCleared() {
        super.onCleared()
        userListener?.remove()
    }

    /**
     * Signs out the user from Firebase Auth.
     */
    fun signOut() {
        auth.signOut()
    }
}

// ---- END NEW ----

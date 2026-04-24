package ug.ac.ndejje.ndejjenest.model

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

/**
 * Repository for handling Firebase Authentication and Firestore logic.
 * This keeps our ViewModels clean and separated from direct Firebase code.
 */
class AuthRepository {
    
    // Get instances of Firebase Authentication and Cloud Firestore
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()

    /**
     * Registers a new user with Firebase Authentication and saves their details in Firestore.
     * 
     * @param user The User data object containing Full Name, Email, and Phone Number.
     * @param password The password typed by the user.
     * @param onComplete A callback function that returns true/false and an optional message.
     */
    fun registerUser(user: User, password: String, onComplete: (isSuccess: Boolean, message: String?) -> Unit) {
        // Step 1: Create the account in Firebase Authentication
        auth.createUserWithEmailAndPassword(user.email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // Step 2: Account created successfully! Now get the unique User ID (uid)
                    val uid = auth.currentUser?.uid
                    
                    if (uid != null) {
                        // Step 3: Save the extra user details (Name, Phone) into Cloud Firestore
                        // We use the 'uid' as the document name so it matches the Auth account
                        firestore.collection("users").document(uid)
                            .set(user)
                            .addOnCompleteListener { firestoreTask ->
                                if (firestoreTask.isSuccessful) {
                                    onComplete(true, "Registration successful!")
                                } else {
                                    // This happens if Firestore rules are locked or Database isn't setup
                                    onComplete(false, "Firestore Error: ${firestoreTask.exception?.message}")
                                }
                            }
                    } else {
                        onComplete(false, "Failed to get user ID.")
                    }
                } else {
                    // Registration failed (e.g., email already in use, password too weak)
                    onComplete(false, task.exception?.message ?: "Registration failed.")
                }
            }
    }

    /**
     * Logs in an existing user using their email and password.
     */
    fun loginUser(email: String, password: String, onComplete: (isSuccess: Boolean, message: String?) -> Unit) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    onComplete(true, "Login successful!")
                } else {
                    onComplete(false, task.exception?.message ?: "Login failed.")
                }
            }
    }
}

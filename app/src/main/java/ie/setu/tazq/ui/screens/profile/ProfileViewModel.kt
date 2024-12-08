package ie.setu.tazq.ui.screens.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel
import ie.setu.tazq.data.model.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val auth: FirebaseAuth,
    private val firestore: FirebaseFirestore
) : ViewModel() {

    private val _user = MutableStateFlow<User?>(null)
    val user: StateFlow<User?> = _user.asStateFlow()

    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    init {
        loadUserProfile()
    }

    private fun loadUserProfile() {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                _error.value = null

                auth.currentUser?.let { firebaseUser ->
                    val userDoc = firestore.collection("users")
                        .document(firebaseUser.uid)
                        .get()
                        .await()

                    // Convert Firestore document to your User model
                    _user.value = User(
                        uid = firebaseUser.uid,
                        name = userDoc.getString("name") ?: firebaseUser.displayName ?: "",
                        email = userDoc.getString("email") ?: firebaseUser.email ?: ""
                    )
                }
            } catch (e: Exception) {
                _error.value = e.message ?: "Failed to load profile"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun updateProfile(name: String) {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                _error.value = null

                auth.currentUser?.let { firebaseUser ->
                    // Update Firestore
                    firestore.collection("users")
                        .document(firebaseUser.uid)
                        .update(mapOf(
                            "name" to name
                        ))
                        .await()

                    // Update local state
                    _user.value = _user.value?.copy(name = name)
                }
            } catch (e: Exception) {
                _error.value = e.message ?: "Failed to update profile"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun signOut() {
        auth.signOut()
        _user.value = null
    }

    fun deleteAccount() {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                _error.value = null

                auth.currentUser?.let { firebaseUser ->
                    // Delete from Firestore first
                    firestore.collection("users")
                        .document(firebaseUser.uid)
                        .delete()
                        .await()

                    // Then delete Firebase Auth account
                    firebaseUser.delete().await()

                    // Clear local state
                    _user.value = null
                }
            } catch (e: Exception) {
                _error.value = e.message ?: "Failed to delete account"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun sendVerificationEmail() {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                _error.value = null

                auth.currentUser?.let { firebaseUser ->
                    firebaseUser.sendEmailVerification().await()
                }
            } catch (e: Exception) {
                _error.value = e.message ?: "Failed to send verification email"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun updateEmail(newEmail: String) {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                _error.value = null

                auth.currentUser?.let { firebaseUser ->
                    // Update Firebase Auth email
                    firebaseUser.updateEmail(newEmail).await()

                    // Update Firestore
                    firestore.collection("users")
                        .document(firebaseUser.uid)
                        .update(mapOf(
                            "email" to newEmail
                        ))
                        .await()

                    // Update local state
                    _user.value = _user.value?.copy(email = newEmail)
                }
            } catch (e: Exception) {
                _error.value = e.message ?: "Failed to update email"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun updatePassword(newPassword: String) {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                _error.value = null

                auth.currentUser?.let { firebaseUser ->
                    firebaseUser.updatePassword(newPassword).await()
                }
            } catch (e: Exception) {
                _error.value = e.message ?: "Failed to update password"
            } finally {
                _isLoading.value = false
            }
        }
    }
}

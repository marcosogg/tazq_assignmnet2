package ie.setu.tazq.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.lifecycle.HiltViewModel
import ie.setu.tazq.data.model.FamilyGroup
import ie.setu.tazq.firebase.services.AuthService
import ie.setu.tazq.firebase.services.FirestoreService
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FamilyGroupViewModel @Inject constructor(
    private val firestoreService: FirestoreService,
    private val authService: AuthService
) : ViewModel() {

    val currentUser: FirebaseUser? get() = authService.currentUser

    fun createFamilyGroup(groupName: String) {
        viewModelScope.launch {
            val group = FamilyGroup(
                groupName = groupName,
                adminId = currentUser?.uid ?: "",
                memberIds = listOf(currentUser?.uid ?: "")
            )
            firestoreService.createFamilyGroup(group)
        }
    }
}

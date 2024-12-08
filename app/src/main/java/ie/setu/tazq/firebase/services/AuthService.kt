package ie.setu.tazq.firebase.services

import com.google.firebase.auth.FirebaseUser

interface AuthService {
    val currentUserId: String
    val currentUser: FirebaseUser?
    val isUserAuthenticatedInFirebase: Boolean
    val email: String?
}
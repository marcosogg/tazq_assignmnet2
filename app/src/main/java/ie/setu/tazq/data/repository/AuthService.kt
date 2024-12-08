package ie.setu.tazq.data.repository

import android.net.Uri
import com.google.firebase.auth.FirebaseUser
import ie.setu.tazq.data.model.Response

typealias FirebaseSignInResponse = Response<FirebaseUser>
typealias SignInWithGoogleResponse = Response<Boolean>

interface AuthService {
    val currentUserId: String
    val currentUser: FirebaseUser?
    val isUserAuthenticatedInFirebase: Boolean
    val email: String?
    val customPhotoUri: Uri?

    suspend fun signInWithGoogle(googleIdToken: String): FirebaseSignInResponse
    suspend fun signOut()
}

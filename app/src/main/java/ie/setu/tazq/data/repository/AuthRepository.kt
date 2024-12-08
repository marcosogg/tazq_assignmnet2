package ie.setu.tazq.data.repository

import android.net.Uri
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.firestore.FirebaseFirestore
import ie.setu.tazq.data.model.User
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class AuthRepository @Inject constructor(
    private val auth: FirebaseAuth,
    private val firestore: FirebaseFirestore
) : AuthService {
    override val currentUserId: String
        get() = auth.currentUser?.uid.orEmpty()

    override val currentUser: FirebaseUser?
        get() = auth.currentUser

    override val isUserAuthenticatedInFirebase: Boolean
        get() = auth.currentUser != null

    override val email: String?
        get() = auth.currentUser?.email

    override val customPhotoUri: Uri?
        get() = auth.currentUser?.photoUrl

    override suspend fun signInWithGoogle(googleIdToken: String): FirebaseSignInResponse {
        return try {
            val credential = GoogleAuthProvider.getCredential(googleIdToken, null)
            val result = auth.signInWithCredential(credential).await()

            // Store user in Firestore
            result.user?.let { firebaseUser ->
                val user = User(
                    uid = firebaseUser.uid,
                    name = firebaseUser.displayName ?: "",
                    email = firebaseUser.email ?: ""
                )
                firestore.collection("users")
                    .document(firebaseUser.uid)
                    .set(user)
                    .await()
            }

            Response.Success(result.user!!)
        } catch (e: Exception) {
            Response.Failure(e)
        }
    }

    override suspend fun signOut() {
        auth.signOut()
    }
}
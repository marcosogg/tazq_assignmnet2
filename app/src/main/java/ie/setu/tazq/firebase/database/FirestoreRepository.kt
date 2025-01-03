package ie.setu.tazq.firebase.database

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.dataObjects
import com.google.firebase.firestore.toObject
import ie.setu.tazq.data.rules.Constants.TASK_COLLECTION
import ie.setu.tazq.data.rules.Constants.USER_EMAIL
import ie.setu.tazq.firebase.services.AuthService
import ie.setu.tazq.firebase.services.FirestoreService
import ie.setu.tazq.firebase.services.Task
import ie.setu.tazq.firebase.services.Tasks
import kotlinx.coroutines.tasks.await
import java.util.Date
import javax.inject.Inject


class FirestoreRepository
@Inject constructor(private val auth: AuthService,
                    private val firestore: FirebaseFirestore
) : FirestoreService {

    override suspend fun getAll(email: String): Tasks {
        return firestore.collection(TASK_COLLECTION)
            .whereEqualTo(USER_EMAIL, email)
            .dataObjects()
    }
    override suspend fun get(email: String,
                             taskId: String): Task? {
        return firestore.collection(TASK_COLLECTION)
            .document(taskId).get().await().toObject()
    }

    override suspend fun insert(email: String,
                                task: Task)
    {
        val taskWithEmail = task.copy(email = email)

        firestore.collection(TASK_COLLECTION)
            .add(taskWithEmail)
            .await()

    }

    override suspend fun update(email: String,
                                task: Task) {
        val taskWithModifiedDate =
            task.copy(dateModified = Date())

        firestore.collection(TASK_COLLECTION)
            .document(task._id)
            .set(taskWithModifiedDate).await()
    }

    override suspend fun delete(email: String,
                                taskId: String) {
        firestore.collection(TASK_COLLECTION)
            .document(taskId)
            .delete().await()
    }
}
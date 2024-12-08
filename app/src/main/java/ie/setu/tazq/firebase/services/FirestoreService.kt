package ie.setu.tazq.firebase.services

import ie.setu.tazq.data.model.TaskModel
import kotlinx.coroutines.flow.Flow

typealias Task = TaskModel
typealias Tasks = Flow<List<Task>>

interface FirestoreService {

    suspend fun getAll(email: String) : Task
    suspend fun get(email: String, TaskId: String) : Task?
    suspend fun insert(email: String, Task: Task)
    suspend fun update(email: String, Task: Task)
    suspend fun delete(email: String, TaskId: String)
}
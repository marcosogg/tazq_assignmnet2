package ie.setu.tazq.data

import java.util.Date
import kotlin.random.Random

data class Task(
    val id: Int = Random.nextInt(1, 100000),
    val title: String = "Untitled",
    val priority: TaskPriority = TaskPriority.MEDIUM,
    val description: String = "No description",
    val dateCreated: Date = Date(),
    val isDone: Boolean = false
)

enum class TaskPriority {
    HIGH,
    MEDIUM,
    LOW
}

// Sample data for testing and previews
val fakeTasks = List(5) { i ->
    Task(
        id = 12345 + i,
        title = "Task $i",
        priority = if (i % 2 == 0) TaskPriority.HIGH else TaskPriority.LOW,
        description = "Description $i",
        dateCreated = Date()
    )
}

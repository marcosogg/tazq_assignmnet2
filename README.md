# Tazq: A Family-Friendly Task Management App

## Overview

Tazq is an Android task management application designed to help families organize, assign, and track tasks. Built with Kotlin and Jetpack Compose, Tazq provides a clean and intuitive interface for managing daily activities. The app leverages Firebase for user authentication and data storage, ensuring data synchronization and accessibility across devices.

## Technical Report

### Functionality

Tazq offers the following core functionalities:

*   **User Authentication:**
    *   **Email/Password Authentication:** Users can create accounts and log in using their email address and password.
    *   **Google Sign-In:** Users can authenticate using their Google accounts, simplifying the login process.
*   **Task Management:**
    *   **CRUD Operations:** Users can Create, Read, Update, and Delete tasks.
    *   **Task Details:** Each task includes a title, description, priority level (High, Medium, Low), due date, and category (e.g., Work, Personal, Shopping).
    *   **Task Status:** Users can mark tasks as complete or incomplete.
    *   **Sorting:** Tasks can be sorted by date created, priority, or category.
    *   **Searching:** Users can search for tasks by title or description. (Functionality partially implemented)
*   **Profile Management:**
    *   Users can view their profile information, including their name, email, and profile picture.
    *   Users can update their profile picture.

### 3rd Party and Google APIs Used

*   **Firebase Authentication:** Used for user authentication and management, supporting both email/password and Google Sign-In.
    *   [Firebase Authentication Documentation](https://firebase.google.com/docs/auth)
*   **Firebase Firestore:** Used as a NoSQL cloud database to store task data and user profiles.
    *   [Firebase Firestore Documentation](https://firebase.google.com/docs/firestore)
*   **Room:** Used for local data persistence, caching task data for offline access.
    *   [Room Persistence Library Documentation](https://developer.android.com/training/data-storage/room)
*   **Coil:** Used for image loading and caching, primarily for user profile pictures.
    *   [Coil Documentation](https://coil-kt.github.io/coil/)
*   **Hilt:** Used for dependency injection, simplifying the management of dependencies throughout the application.
    *   [Hilt Documentation](https://developer.android.com/training/dependency-injection/hilt-android)
*   **Jetpack Compose:** Used for building the user interface declaratively.
    *   [Jetpack Compose Documentation](https://developer.android.com/jetpack/compose)

### UML & Class Diagrams

**Simplified Class Diagram:**

```plantuml
@startuml
class Task {
    - id: Int
    - userId: String
    - title: String
    - priority: TaskPriority
    - description: String
    - category: String
    - dateCreated: Date
    - isDone: Boolean
}

enum TaskPriority {
    HIGH
    MEDIUM
    LOW
}

class TaskViewModel {
    - repository: TaskRepository
    - authService: AuthService
    - taskTitle: StateFlow<String>
    - taskDescription: StateFlow<String>
    - taskPriority: StateFlow<TaskPriority>
    - selectedCategory: StateFlow<String>
    - isTitleValid: StateFlow<Boolean>
    - isDescriptionValid: StateFlow<Boolean>
    + updateTaskTitle(title: String)
    + updateTaskDescription(description: String)
    + updateTaskPriority(priority: TaskPriority)
    + updateSelectedCategory(category: String)
    + addTask()
}

class TaskListViewModel {
    - repository: TaskRepository
    - authService: AuthService
    - tasks: StateFlow<List<Task>>
    + deleteTask(task: Task)
    + updateTaskStatus(task: Task)
    + updateTask(task: Task)
    + searchTasks(query: String)
    + sortTasks(sortBy: SortOption)
}

class TaskRepository {
    - taskDAO: TaskDAO
    + getAll(userId: String): Flow<List<Task>>
    + get(id: Int, userId: String): Flow<Task>
    + insert(task: Task)
    + update(task: Task)
    + delete(task: Task)
    + updateTaskStatus(id: Int, isDone: Boolean, userId: String)
}

interface TaskDAO {
    + getAll(userId: String): Flow<List<Task>>
    + get(id: Int, userId: String): Flow<Task>
    + insert(task: Task)
    + update(task: Task)
    + updateTaskStatus(id: Int, isDone: Boolean, userId: String)
    + delete(task: Task)
}

class AuthService {
    + currentUserId: String
    + currentUser: FirebaseUser
    + authenticateUser(email: String, password: String)
    + createUser(name: String, email: String, password: String)
    + signOut()
    + authenticateGoogleUser(googleIdToken: String)
}

TaskViewModel "1" -- "1" TaskRepository
TaskListViewModel "1" -- "1" TaskRepository
TaskRepository "1" -- "1" TaskDAO
TaskViewModel "1" -- "1" AuthService
TaskListViewModel "1" -- "1" AuthService

@enduml


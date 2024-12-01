package ie.setu.tazq.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List  // Updated import
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Category
import androidx.compose.material.icons.filled.Info
import androidx.compose.ui.graphics.vector.ImageVector

interface AppDestination {
    val icon: ImageVector
    val label: String
    val route: String
}

object TaskList : AppDestination {
    override val icon = Icons.AutoMirrored.Filled.List  // Updated icon
    override val label = "Tasks"
    override val route = "tasklist"
}

object CreateTask : AppDestination {
    override val icon = Icons.Default.Add
    override val label = "New Task"
    override val route = "createtask"
}

object Categories : AppDestination {
    override val icon = Icons.Default.Category
    override val label = "Categories"
    override val route = "categories"
}

object About : AppDestination {
    override val icon = Icons.Default.Info
    override val label = "About"
    override val route = "about"
}

val bottomAppBarDestinations = listOf(TaskList, CreateTask, Categories, About)
val allDestinations = listOf(TaskList, CreateTask, Categories, About)

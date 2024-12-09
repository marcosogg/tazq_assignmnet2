package ie.setu.tazq.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.automirrored.filled.Login
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Category
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Person
import androidx.compose.ui.graphics.vector.ImageVector

interface AppDestination {
    val icon: ImageVector
    val label: String
    val route: String
}

object TaskList : AppDestination {
    override val icon = Icons.AutoMirrored.Filled.List
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

object Home : AppDestination {
    override val icon = Icons.Filled.Home
    override val label = "Home"
    override val route = "Home"
}

object Profile : AppDestination {
    override val icon = Icons.Default.Person
    override val label = "Profile"
    override val route = "Profile"
}

object Login : AppDestination {
    override val icon = Icons.AutoMirrored.Filled.Login
    override val label = "Login"
    override val route = "Login"
}

object Register : AppDestination {
    override val icon = Icons.Default.AccountCircle
    override val label = "Register"
    override val route = "Register"
}

val bottomAppBarDestinations = listOf(TaskList, CreateTask, Categories, About, Profile)
val userSignedOutDestinations = listOf(Login, Register)
val allDestinations = listOf(TaskList, CreateTask, Categories, About, Home, Profile, Login, Register)

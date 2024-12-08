package ie.setu.tazq.navigation

import androidx.navigation.NavHostController

fun NavHostController.navigateToProfile() {
    navigate(NavigationDestinations.Profile.route) {
        launchSingleTop = true
    }
}

fun NavHostController.navigateToSignIn() {
    navigate(NavigationDestinations.SignIn.route) {
        popUpTo(0) { inclusive = true }
    }
}

fun NavHostController.navigateToTaskList() {
    navigate(NavigationDestinations.TaskList.route) {
        popUpTo(0) { inclusive = true }
    }
}

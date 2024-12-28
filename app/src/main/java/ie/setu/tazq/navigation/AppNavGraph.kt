package ie.setu.tazq.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import ie.setu.tazq.ui.screens.CreateFamilyGroupScreen
import ie.setu.tazq.ui.screens.about.AboutScreen
import ie.setu.tazq.ui.screens.categories.CategoriesScreen
import ie.setu.tazq.ui.screens.login.LoginScreen
import ie.setu.tazq.ui.screens.profile.ProfileScreen
import ie.setu.tazq.ui.screens.register.RegisterScreen
import ie.setu.tazq.ui.screens.task.TaskScreen
import ie.setu.tazq.ui.screens.tasklist.TaskListScreen

@Composable
fun NavHostProvider(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    startDestination: AppDestination,
    paddingValues: PaddingValues,
    userId: String
) {
    NavHost(
        navController = navController,
        startDestination = startDestination.route,
        modifier = Modifier.padding(paddingValues = paddingValues)
    ) {
        composable(route = TaskList.route) {
            TaskListScreen(modifier = modifier)
        }

        composable(route = CreateTask.route) {
            TaskScreen(modifier = modifier)
        }

        composable(route = Categories.route) {
            CategoriesScreen(
                modifier = modifier,
                userId = userId)

        }

        composable(route = About.route) {
            AboutScreen(modifier = modifier)
        }

        composable(route = Login.route) {
            LoginScreen(
                navController = navController,
                onLogin = { navController.popBackStack() }
            )
        }

        composable(route = Register.route) {
            RegisterScreen(
                navController = navController,
                onRegister = { navController.popBackStack() }
            )
        }

        composable(route = Profile.route) {
            ProfileScreen(
                onSignOut = {
                    navController.popBackStack()
                    navController.navigate(Login.route) {
                        popUpTo(Home.route) { inclusive = true }
                    }
                },
            )
        }

        // Add the composable for the new screen
        composable(route = CreateFamilyGroup.route) {
            CreateFamilyGroupScreen(onGroupCreated = {
                // Optional: Navigate to a different screen after creation
                // navController.navigate("family_group_details/${newGroupId}")
                navController.navigate(TaskList.route) // Navigate back to task list
            })
        }
    }
}

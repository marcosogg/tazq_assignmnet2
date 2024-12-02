package ie.setu.tazq.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import ie.setu.tazq.ui.screens.about.AboutScreen
import ie.setu.tazq.ui.screens.categories.CategoriesScreen
import ie.setu.tazq.ui.screens.task.TaskScreen
import ie.setu.tazq.ui.screens.tasklist.TaskListScreen

@Composable
fun AppNavGraph(
    modifier: Modifier,
    navController: NavHostController,
    paddingValues: PaddingValues
) {
    NavHost(
        navController = navController,
        startDestination = TaskList.route,
        modifier = Modifier.padding(paddingValues = paddingValues)
    ) {
        composable(route = TaskList.route) {
            TaskListScreen(
                modifier = modifier
            )
        }

        composable(route = CreateTask.route) {
            TaskScreen(
                modifier = modifier
            )
        }

        composable(route = Categories.route) {
            CategoriesScreen(
                modifier = modifier
            )
        }

        composable(route = About.route) {
            AboutScreen(
                modifier = modifier
            )
        }
    }
}

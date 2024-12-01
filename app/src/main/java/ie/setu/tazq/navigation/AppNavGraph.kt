package ie.setu.tazq.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import ie.setu.tazq.data.Task
import ie.setu.tazq.ui.screens.AboutScreen
import ie.setu.tazq.ui.screens.CategoriesScreen
import ie.setu.tazq.ui.screens.TaskListScreen
import ie.setu.tazq.ui.screens.TaskScreen

@Composable
fun AppNavGraph(
    modifier: Modifier,
    navController: NavHostController,
    paddingValues: PaddingValues,
    tasks: SnapshotStateList<Task>
) {
    NavHost(
        navController = navController,
        startDestination = TaskList.route,
        modifier = Modifier.padding(paddingValues = paddingValues)
    ) {
        composable(route = TaskList.route) {
            TaskListScreen(modifier = modifier, tasks = tasks)
        }
        composable(route = CreateTask.route) {
            TaskScreen(modifier = modifier, tasks = tasks)
        }
        composable(route = Categories.route) {
            CategoriesScreen(modifier = modifier, tasks = tasks)
        }
        composable(route = About.route) {
            AboutScreen(modifier = modifier)
        }
    }
}

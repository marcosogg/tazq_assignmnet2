package ie.setu.tazq

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import ie.setu.tazq.data.Task
import ie.setu.tazq.navigation.AppNavGraph
import ie.setu.tazq.navigation.TaskList
import ie.setu.tazq.navigation.allDestinations
import ie.setu.tazq.ui.components.general.BottomAppBarProvider
import ie.setu.tazq.ui.components.general.TopAppBarProvider
import ie.setu.tazq.ui.theme.TazqTheme

private const val TAG = "Tazq"

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Log.i(TAG, "Tazq Started...")

        setContent {
            TazqTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    TazqApp(modifier = Modifier)
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TazqApp(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController()
) {
    val tasks = remember { mutableStateListOf<Task>() }

    val currentNavBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = currentNavBackStackEntry?.destination
    val currentBottomScreen = allDestinations.find { it.route == currentDestination?.route } ?: TaskList

    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBarProvider(
                currentScreen = currentBottomScreen,
                canNavigateBack = navController.previousBackStackEntry != null
            ) { navController.navigateUp() }
        },
        content = { paddingValues ->
            AppNavGraph(
                modifier = modifier,
                navController = navController,
                paddingValues = paddingValues,
                tasks = tasks
            )
        },
        bottomBar = {
            BottomAppBarProvider(
                navController = navController,
                currentScreen = currentBottomScreen
            )
        }
    )
}


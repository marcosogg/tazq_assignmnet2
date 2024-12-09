package ie.setu.tazq.ui.screens.home

//import ie.setu.tazq.navigation.Report
import android.annotation.SuppressLint
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import ie.setu.tazq.navigation.Login
import ie.setu.tazq.navigation.NavHostProvider
import ie.setu.tazq.navigation.TaskList
import ie.setu.tazq.navigation.allDestinations
import ie.setu.tazq.navigation.bottomAppBarDestinations
import ie.setu.tazq.navigation.userSignedOutDestinations
import ie.setu.tazq.ui.components.general.BottomAppBarProvider
import ie.setu.tazq.ui.components.general.TopAppBarProvider
import ie.setu.tazq.ui.theme.TazqTheme

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    homeViewModel: HomeViewModel = hiltViewModel(),
    navController: NavHostController = rememberNavController(),
) {
    val currentNavBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = currentNavBackStackEntry?.destination
    val currentBottomScreen = allDestinations.find {
        it.route == currentDestination?.route
    } ?: Login

    var startDestination = currentBottomScreen
    val currentUser = homeViewModel.currentUser
    val isActiveSession = homeViewModel.isAuthenticated()
    val userDestinations = if (!isActiveSession)
        userSignedOutDestinations
    else bottomAppBarDestinations

    if (isActiveSession) startDestination = TaskList

    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBarProvider(
                currentScreen = currentBottomScreen,
                canNavigateBack = navController.previousBackStackEntry != null,
            ) { navController.navigateUp() }
        },
        content = { paddingValues ->
            NavHostProvider(
                modifier = modifier,
                navController = navController,
                startDestination = startDestination,
                paddingValues = paddingValues
            )
        },
        bottomBar = {
            if (isActiveSession) {
                BottomAppBarProvider(
                    navController = navController,
                    currentScreen = currentBottomScreen
                )
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun MyAppPreview() {
    TazqTheme {
        HomeScreen(modifier = Modifier)
    }
}
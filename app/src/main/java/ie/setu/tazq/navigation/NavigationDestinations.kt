package ie.setu.tazq.navigation

sealed class NavigationDestinations(val route: String) {
    object SignIn : NavigationDestinations("signin")
    object SignUp : NavigationDestinations("signup")
    object TaskList : NavigationDestinations("tasklist")
    object CreateTask : NavigationDestinations("createtask")
    object Categories : NavigationDestinations("categories")
    object About : NavigationDestinations("about")
    object Profile : NavigationDestinations("profile")
}

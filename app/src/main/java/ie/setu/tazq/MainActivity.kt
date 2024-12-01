package ie.setu.tazq

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.List
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import ie.setu.tazq.data.Task
import ie.setu.tazq.ui.components.general.MenuItem
import ie.setu.tazq.ui.screens.TaskListScreen
import ie.setu.tazq.ui.screens.TaskScreen
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
fun TazqApp(modifier: Modifier = Modifier) {
    val tasks = remember { mutableStateListOf<Task>() }
    var selectedMenuItem by remember { mutableStateOf<MenuItem?>(MenuItem.CreateTask) }

    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(id = R.string.app_name),
                        color = Color.White
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = Color.White,
                ),
                actions = {
                    if(selectedMenuItem == MenuItem.CreateTask) {
                        IconButton(onClick = {
                            selectedMenuItem = MenuItem.TaskList
                        }) {
                            Icon(
                                imageVector = Icons.Default.List,
                                contentDescription = "View Tasks",
                                tint = Color.White,
                                modifier = Modifier.size(40.dp)
                            )
                        }
                    }
                    else {
                        IconButton(onClick = {
                            selectedMenuItem = MenuItem.CreateTask
                        }) {
                            Icon(
                                imageVector = Icons.Default.Add,
                                contentDescription = "Create Task",
                                tint = Color.White,
                                modifier = Modifier.size(40.dp)
                            )
                        }
                    }
                }
            )
        }
    ) { paddingValues ->
        Box(modifier = Modifier.padding(paddingValues)) {
            when (selectedMenuItem) {
                MenuItem.CreateTask -> TaskScreen(modifier = modifier, tasks = tasks)
                MenuItem.TaskList -> TaskListScreen(modifier = modifier, tasks = tasks)
                else -> {}
            }
        }
    }
}

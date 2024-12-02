package ie.setu.tazq.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ie.setu.tazq.data.Task
import ie.setu.tazq.ui.components.task.EditTaskDialog
import ie.setu.tazq.ui.components.tasklist.TaskList

@Composable
fun TaskListScreen(
    modifier: Modifier = Modifier,
    tasks: SnapshotStateList<Task>
) {
    var taskToEdit: Task? by remember { mutableStateOf(null) }

    Column(
        modifier = modifier.padding(
            top = 16.dp,
            start = 16.dp,
            end = 16.dp
        )
    ) {
        // Header
        Text(
            text = "Your Tasks",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        if (tasks.isEmpty()) {
            // Empty state
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "No tasks yet",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Text(
                    text = "Add a task to get started",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        } else {
            // Task list
            TaskList(
                modifier = Modifier.fillMaxSize(),
                tasks = tasks,
                onDeleteTask = { task: Task ->
                    tasks.remove(task)
                },
                onEditTask = { task: Task ->
                    taskToEdit = task
                },
                onToggleTask = { task: Task ->
                    val index = tasks.indexOf(task)
                    if (index != -1) {
                        tasks[index] = task.copy(isDone = !task.isDone)
                    }
                }
            )
        }
    }

    // Edit task dialog
    taskToEdit?.let { task ->
        EditTaskDialog(
            task = task,
            onDismissRequest = { taskToEdit = null },
            onConfirm = { editedTask: Task ->
                val index = tasks.indexOf(task)
                if (index != -1) {
                    tasks[index] = editedTask
                }
                taskToEdit = null
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun TaskListScreenPreview() {
    MaterialTheme {
        TaskListScreen(
            modifier = Modifier,
            tasks = remember { mutableStateListOf() }
        )
    }
}

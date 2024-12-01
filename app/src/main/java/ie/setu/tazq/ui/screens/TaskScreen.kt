package ie.setu.tazq.ui.screens

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ie.setu.tazq.data.Task
import ie.setu.tazq.data.TaskPriority
import ie.setu.tazq.ui.components.task.*
import ie.setu.tazq.ui.theme.TazqTheme

private const val TAG = "Tazq"

@Composable
fun TaskScreen(
    modifier: Modifier = Modifier,
    tasks: SnapshotStateList<Task>
) {
    var taskTitle by remember { mutableStateOf("") }
    var taskPriority by remember { mutableStateOf(TaskPriority.MEDIUM) }
    var taskDescription by remember { mutableStateOf("") }
    var totalTasks by remember { mutableIntStateOf(0) }

    Column {
        Column(
            modifier = modifier.padding(
                top = 48.dp,
                start = 24.dp,
                end = 24.dp
            ),
            verticalArrangement = Arrangement.spacedBy(30.dp),
        ) {
            // Header section
            Text(
                text = "Task Manager",
                style = MaterialTheme.typography.headlineMedium
            )

            // Task Input and Priority Selection
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                TaskInput(
                    modifier = Modifier.weight(1f),
                    onTaskTitleChange = { taskTitle = it }
                )
                Spacer(modifier = Modifier.width(16.dp))
                RadioButtonGroup(
                    modifier = modifier,
                    onPriorityChange = {
                        taskPriority = when(it) {
                            "High Priority" -> TaskPriority.HIGH
                            "Low Priority" -> TaskPriority.LOW
                            else -> TaskPriority.MEDIUM
                        }
                    }
                )
            }

            // Description Input
            TaskDescription(
                modifier = modifier,
                onDescriptionChange = { taskDescription = it }
            )

            // Add Task Button with logging
            Button(
                onClick = {
                    val newTask = Task(
                        title = taskTitle,
                        priority = taskPriority,
                        description = taskDescription
                    )
                    tasks.add(newTask)
                    totalTasks = tasks.size

                    // Log task creation
                    Log.i(TAG, """Task Added:
                        |Title: $taskTitle
                        |Priority: $taskPriority
                        |Description: $taskDescription
                        |Total Tasks: $totalTasks
                        |""".trimMargin())
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Add Task")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TaskScreenPreview() {
    TazqTheme {
        TaskScreen(
            modifier = Modifier,
            tasks = remember { mutableStateListOf() }
        )
    }
}

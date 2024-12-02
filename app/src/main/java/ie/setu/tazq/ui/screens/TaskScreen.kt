package ie.setu.tazq.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ie.setu.tazq.data.Task
import ie.setu.tazq.data.TaskPriority
import ie.setu.tazq.ui.components.task.RadioButtonGroup
import ie.setu.tazq.ui.components.task.TaskDescription
import ie.setu.tazq.ui.components.task.TaskInput

@Composable
fun TaskScreen(
    modifier: Modifier = Modifier,
    tasks: SnapshotStateList<Task>
) {
    var taskTitle by remember { mutableStateOf("") }
    var taskPriority by remember { mutableStateOf(TaskPriority.MEDIUM) }
    var taskDescription by remember { mutableStateOf("") }
    var selectedCategory by remember { mutableStateOf("Personal") }
    var expanded by remember { mutableStateOf(false) }

    Column(
        modifier = modifier.padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Task Title
        TaskInput(
            onTaskTitleChange = { taskTitle = it }
        )

        // Priority Selection
        RadioButtonGroup(
            onPriorityChange = {
                taskPriority = when(it) {
                    "High Priority" -> TaskPriority.HIGH
                    "Low Priority" -> TaskPriority.LOW
                    else -> TaskPriority.MEDIUM
                }
            }
        )

        // Category Selection
        Box(modifier = Modifier.fillMaxWidth()) {
            OutlinedTextField(
                value = selectedCategory,
                onValueChange = {},
                readOnly = true,
                label = { Text("Category") },
                modifier = Modifier.fillMaxWidth()
            )

            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                modifier = Modifier.fillMaxWidth()
            ) {
                val categories = listOf("Work", "Personal", "Shopping", "Health", "Study")
                categories.forEach { category ->
                    DropdownMenuItem(
                        text = { Text(category) },
                        onClick = {
                            selectedCategory = category
                            expanded = false
                        }
                    )
                }
            }
        }

        // Task Description
        TaskDescription(
            onDescriptionChange = { taskDescription = it }
        )

        // Add Task Button
        Button(
            onClick = {
                if (taskTitle.isNotEmpty() && taskTitle.length >= 3 &&
                    taskDescription.length <= 500) {
                    val newTask = Task(
                        title = taskTitle,
                        priority = taskPriority,
                        description = taskDescription,
                        category = selectedCategory
                    )
                    tasks.add(newTask)
                    // Reset form
                    taskTitle = ""
                    taskDescription = ""
                    selectedCategory = "Personal"
                }
            },
            enabled = taskTitle.isNotEmpty() && taskTitle.length >= 3 &&
                    taskDescription.length <= 500,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Add Task")
        }
    }
}

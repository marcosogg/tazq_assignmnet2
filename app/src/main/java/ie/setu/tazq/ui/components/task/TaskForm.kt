package ie.setu.tazq.ui.components.task

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ie.setu.tazq.data.Categories
import ie.setu.tazq.data.Task
import ie.setu.tazq.data.TaskPriority

@Composable
fun TaskForm(
    modifier: Modifier = Modifier,
    onTaskCreated: (Task) -> Unit
) {
    var taskTitle by remember { mutableStateOf("") }
    var taskPriority by remember { mutableStateOf(TaskPriority.MEDIUM) }
    var taskDescription by remember { mutableStateOf("") }
    var selectedCategory by remember { mutableStateOf("Personal") }
    var expanded by remember { mutableStateOf(false) }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Task Title
        OutlinedTextField(
            value = taskTitle,
            onValueChange = { taskTitle = it },
            label = { Text("Task Title") },
            modifier = Modifier.fillMaxWidth()
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
                Categories.list.forEach { category ->
                    DropdownMenuItem(
                        text = { Text(category.name) },
                        onClick = {
                            selectedCategory = category.name
                            expanded = false
                        }
                    )
                }
            }
        }

        // Task Description
        OutlinedTextField(
            value = taskDescription,
            onValueChange = { taskDescription = it },
            label = { Text("Description") },
            modifier = Modifier.fillMaxWidth(),
            minLines = 3
        )

        // Add Button
        Button(
            onClick = {
                val newTask = Task(
                    title = taskTitle,
                    priority = taskPriority,
                    description = taskDescription,
                    category = selectedCategory
                )
                onTaskCreated(newTask)
                // Reset form
                taskTitle = ""
                taskDescription = ""
                selectedCategory = "Personal"
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Add Task")
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun TaskFormPreview() {
    MaterialTheme {
        TaskForm(
            onTaskCreated = {}
        )
    }
}

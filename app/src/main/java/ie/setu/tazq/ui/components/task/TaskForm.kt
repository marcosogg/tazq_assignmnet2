package ie.setu.tazq.ui.components.task

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
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ie.setu.tazq.data.Categories
import ie.setu.tazq.data.Task

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

    // Validation states
    var isTitleValid by remember { mutableStateOf(false) }
    var isDescriptionValid by remember { mutableStateOf(true) }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Task Title
        TaskInput(
            value = taskTitle,
            onTaskTitleChange = {
                taskTitle = it
                isTitleValid = it.length >= 3
            },
            isError = !isTitleValid && taskTitle.isNotEmpty(),
            errorMessage = if (!isTitleValid && taskTitle.isNotEmpty()) {
                "Title must be at least 3 characters"
            } else null
        )

        // Priority Selection
        RadioButtonGroup(
            selectedPriority = taskPriority,
            onPriorityChange = { taskPriority = it }
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
        TaskDescription(
            value = taskDescription,
            onDescriptionChange = {
                taskDescription = it
                isDescriptionValid = it.length <= 500
            },
            isError = !isDescriptionValid,
            errorMessage = if (!isDescriptionValid) {
                "Description must be less than 500 characters"
            } else null
        )

        // Add Button
        Button(
            onClick = {
                if (isTitleValid && isDescriptionValid) {
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
                    taskPriority = TaskPriority.MEDIUM
                    selectedCategory = "Personal"
                    isTitleValid = false
                }
            },
            enabled = isTitleValid && isDescriptionValid,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Add Task")
        }
    }
}

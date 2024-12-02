package ie.setu.tazq.ui.components.task

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import ie.setu.tazq.data.Task

@Composable
fun EditTaskDialog(
    task: Task,
    onDismissRequest: () -> Unit,
    onConfirm: (Task) -> Unit
) {
    var editedTitle by remember { mutableStateOf(task.title) }
    var editedDescription by remember { mutableStateOf(task.description) }
    var editedPriority by remember { mutableStateOf(task.priority) }
    var editedCategory by remember { mutableStateOf(task.category) }

    AlertDialog(
        onDismissRequest = onDismissRequest,
        title = { Text("Edit Task") },
        text = {
            Column {
                TaskInput(
                    onTaskTitleChange = { editedTitle = it }
                )
                TaskDescription(
                    onDescriptionChange = { editedDescription = it }
                )
                // Add priority and category selection here
            }
        },
        confirmButton = {
            TextButton(
                onClick = {
                    onConfirm(
                        task.copy(
                            title = editedTitle,
                            description = editedDescription,
                            priority = editedPriority,
                            category = editedCategory
                        )
                    )
                    onDismissRequest()
                }
            ) {
                Text("Save")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismissRequest) {
                Text("Cancel")
            }
        }
    )
}

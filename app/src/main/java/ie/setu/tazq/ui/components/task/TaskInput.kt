package ie.setu.tazq.ui.components.task

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier

@Composable
fun TaskInput(
    modifier: Modifier = Modifier,
    onTaskTitleChange: (String) -> Unit = {}
) {
    var taskTitle by remember { mutableStateOf("") }

    OutlinedTextField(
        value = taskTitle,
        onValueChange = {
            taskTitle = it
            onTaskTitleChange(it)
        },
        label = { Text("Task Title") },
        modifier = modifier.fillMaxWidth(),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = MaterialTheme.colorScheme.primary,
            unfocusedBorderColor = MaterialTheme.colorScheme.secondary
        )
    )
}

package ie.setu.tazq.ui.components.task

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier

@Composable
fun TaskInput(
    modifier: Modifier = Modifier,
    onTaskTitleChange: (String) -> Unit = {}
) {
    var taskTitle by remember { mutableStateOf("") }
    var isError by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }

    Column(modifier = modifier.fillMaxWidth()) {
        OutlinedTextField(
            value = taskTitle,
            onValueChange = {
                taskTitle = it
                // Validation
                when {
                    it.isEmpty() -> {
                        isError = true
                        errorMessage = "Title cannot be empty"
                    }
                    it.length < 3 -> {
                        isError = true
                        errorMessage = "Title must be at least 3 characters"
                    }
                    else -> {
                        isError = false
                        errorMessage = ""
                        onTaskTitleChange(it)
                    }
                }
            },
            label = { Text("Task Title") },
            modifier = Modifier.fillMaxWidth(),
            isError = isError,
            supportingText = {
                if (isError) {
                    Text(
                        text = errorMessage,
                        color = MaterialTheme.colorScheme.error
                    )
                }
            },
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = MaterialTheme.colorScheme.primary,
                unfocusedBorderColor = MaterialTheme.colorScheme.secondary,
                errorBorderColor = MaterialTheme.colorScheme.error
            )
        )
    }
}

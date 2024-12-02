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
fun TaskDescription(
    modifier: Modifier = Modifier,
    onDescriptionChange: (String) -> Unit = {}
) {
    var description by remember { mutableStateOf("") }
    var isError by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }

    Column(modifier = modifier.fillMaxWidth()) {
        OutlinedTextField(
            value = description,
            onValueChange = {
                description = it
                // Validation
                when {
                    it.length > 500 -> {
                        isError = true
                        errorMessage = "Description too long (max 500 characters)"
                    }
                    else -> {
                        isError = false
                        errorMessage = ""
                        onDescriptionChange(it)
                    }
                }
            },
            label = { Text("Description") },
            modifier = Modifier.fillMaxWidth(),
            minLines = 3,
            maxLines = 5,
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

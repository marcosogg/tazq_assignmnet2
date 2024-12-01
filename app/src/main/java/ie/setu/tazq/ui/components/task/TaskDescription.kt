package ie.setu.tazq.ui.components.task

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier

@Composable
fun TaskDescription(
    modifier: Modifier = Modifier,
    onDescriptionChange: (String) -> Unit = {}
) {
    var description by remember { mutableStateOf("") }

    OutlinedTextField(
        value = description,
        onValueChange = {
            description = it
            onDescriptionChange(it)
        },
        label = { Text("Description") },
        modifier = modifier.fillMaxWidth(),
        minLines = 3,
        maxLines = 5,
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = MaterialTheme.colorScheme.primary,
            unfocusedBorderColor = MaterialTheme.colorScheme.secondary
        )
    )
}

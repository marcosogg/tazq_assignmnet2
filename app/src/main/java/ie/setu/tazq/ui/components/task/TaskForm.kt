package ie.setu.tazq.ui.components.task

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ie.setu.tazq.data.Task
import ie.setu.tazq.ui.theme.TazqTheme

@Composable
fun TaskForm(
    modifier: Modifier = Modifier,
    onTaskCreated: (Task) -> Unit = {}
) {
    Column(
        modifier = modifier.padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Title text
        Text(
            text = "Create New Task",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )

        // Task input fields
        TaskInput()

        // Priority selection
        RadioButtonGroup(
            modifier = Modifier.padding(vertical = 8.dp),
            onPriorityChange = {}
        )

        // Description input
        TaskDescription()

        // Add task button
        AddTaskButton(
            modifier = Modifier.fillMaxWidth(),
            onAddTask = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
fun TaskFormPreview() {
    TazqTheme {
        TaskForm()
    }
}

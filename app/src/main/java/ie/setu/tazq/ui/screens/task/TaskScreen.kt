package ie.setu.tazq.ui.screens.task

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import ie.setu.tazq.data.Categories
import ie.setu.tazq.ui.components.task.RadioButtonGroup
import ie.setu.tazq.ui.components.task.TaskDescription
import ie.setu.tazq.ui.components.task.TaskInput

@Composable
fun TaskScreen(
    modifier: Modifier = Modifier,
    viewModel: TaskViewModel = hiltViewModel()
) {
    val taskTitle by viewModel.taskTitle.collectAsState()
    val taskDescription by viewModel.taskDescription.collectAsState()
    val priority by viewModel.taskPriority.collectAsState()
    val selectedCategory by viewModel.selectedCategory.collectAsState()
    val isTitleValid by viewModel.isTitleValid.collectAsState()
    val isDescriptionValid by viewModel.isDescriptionValid.collectAsState()

    var expanded by remember { mutableStateOf(false) }

    Column(
        modifier = modifier.padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        TaskInput(
            value = taskTitle,
            onTaskTitleChange = { viewModel.updateTaskTitle(it) },
            isError = !isTitleValid,
            errorMessage = viewModel.getTitleErrorMessage()
        )

        RadioButtonGroup(
            selectedPriority = priority,
            onPriorityChange = { viewModel.updateTaskPriority(it) }
        )

        // Category Selection with Box for dropdown positioning
        Box(modifier = Modifier.fillMaxWidth()) {
            OutlinedTextField(
                value = selectedCategory,
                onValueChange = {},
                readOnly = true,
                label = { Text("Category") },
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null
                    ) { expanded = !expanded }
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
                            viewModel.updateSelectedCategory(category.name)
                            expanded = false
                        }
                    )
                }
            }
        }

        TaskDescription(
            value = taskDescription,
            onDescriptionChange = { viewModel.updateTaskDescription(it) },
            isError = !isDescriptionValid,
            errorMessage = viewModel.getDescriptionErrorMessage()
        )

        Button(
            onClick = { viewModel.addTask() },
            enabled = viewModel.isFormValid(),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Add Task")
        }
    }
}

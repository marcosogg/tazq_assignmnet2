package ie.setu.tazq.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import ie.setu.tazq.data.model.FamilyGroup
import ie.setu.tazq.ui.screens.home.HomeViewModel
import ie.setu.tazq.ui.viewmodel.FamilyGroupViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateFamilyGroupScreen(
    onGroupCreated: () -> Unit = {},
    viewModel: FamilyGroupViewModel = hiltViewModel(),
) {
    var groupName by remember { mutableStateOf("") }
    var isNameValid by remember { mutableStateOf(false) }
    var showError by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        OutlinedTextField(
            value = groupName,
            onValueChange = {
                groupName = it
                isNameValid = it.isNotBlank() // Basic validation: not empty
                showError = it.isBlank()
            },
            label = { Text("Family Group Name") },
            isError = showError,
            modifier = Modifier.fillMaxWidth()
        )
        if (showError) {
            Text("Name cannot be empty", color = MaterialTheme.colorScheme.error)
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                if (isNameValid) {
                    viewModel.createFamilyGroup(groupName)
                    onGroupCreated()
                } else {
                    showError = true
                }
            },
            enabled = isNameValid,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Create Group")
        }
    }
}


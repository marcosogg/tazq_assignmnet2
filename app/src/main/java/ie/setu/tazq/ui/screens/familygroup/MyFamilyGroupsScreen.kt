package ie.setu.tazq.ui.screens.familygroup

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import ie.setu.tazq.data.model.FamilyGroup
import ie.setu.tazq.ui.viewmodel.FamilyGroupViewModel
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.ui.graphics.Color
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.runtime.*
import androidx.compose.material3.*
import androidx.compose.foundation.layout.*

@Composable
fun MyFamilyGroupsScreen(
    viewModel: FamilyGroupViewModel = hiltViewModel()
) {
    val familyGroups by viewModel.familyGroups.collectAsState()
    val currentUserId = viewModel.currentUser?.uid ?: ""

    Column(modifier = Modifier.padding(16.dp)) {
        Text(text = "My Family Groups")

        LazyColumn {
            items(familyGroups) { group ->
                FamilyGroupItem(group = group, currentUserId = currentUserId, viewModel = viewModel)
            }
        }
    }
}

@Composable
fun FamilyGroupItem(group: FamilyGroup, currentUserId: String, viewModel: FamilyGroupViewModel) {
    var showRenameDialog by remember { mutableStateOf(false) }
    var showDeleteConfirmation by remember { mutableStateOf(false) }
    var showLeaveConfirmation by remember { mutableStateOf(false) }
    var newName by remember { mutableStateOf(group.groupName) }

    Row(modifier = Modifier.fillMaxWidth().padding(8.dp), horizontalArrangement = Arrangement.SpaceBetween) {
        Text(text = group.groupName)

        Row {
            if (group.adminId == currentUserId) {
                // Edit (Rename) Button
                IconButton(onClick = { showRenameDialog = true }) {
                    Icon(Icons.Filled.Edit, contentDescription = "Edit")
                }

                // Delete Button
                IconButton(onClick = { showDeleteConfirmation = true }) {
                    Icon(Icons.Filled.Delete, contentDescription = "Delete")
                }
            } else {
                // Leave Group Button
                IconButton(onClick = { showLeaveConfirmation = true }) {
                    Icon(Icons.Filled.ExitToApp, contentDescription = "Leave Group")
                }
            }
        }
    }

    // Rename Dialog
    if (showRenameDialog) {
        AlertDialog(
            onDismissRequest = { showRenameDialog = false },
            title = { Text("Rename Group") },
            text = {
                Column {
                    TextField(
                        value = newName,
                        onValueChange = { newName = it },
                        label = { Text("New Name") }
                    )
                }
            },
            confirmButton = {
                Button(onClick = {
                    viewModel.updateFamilyGroupName(group.groupId, newName)
                    showRenameDialog = false
                }) {
                    Text("Save")
                }
            },
            dismissButton = {
                Button(onClick = { showRenameDialog = false }) {
                    Text("Cancel")
                }
            }
        )
    }

    // Delete Confirmation Dialog
    if (showDeleteConfirmation) {
        AlertDialog(
            onDismissRequest = { showDeleteConfirmation = false },
            title = { Text("Delete Group") },
            text = { Text("Are you sure you want to delete this group? This action cannot be undone.") },
            confirmButton = {
                Button(
                    onClick = {
                        viewModel.deleteFamilyGroup(group.groupId)
                        showDeleteConfirmation = false
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Red)
                ) {
                    Text("Delete")
                }
            },
            dismissButton = {
                Button(onClick = { showDeleteConfirmation = false }) {
                    Text("Cancel")
                }
            }
        )
    }

    // Leave Group Confirmation Dialog
    if (showLeaveConfirmation) {
        AlertDialog(
            onDismissRequest = { showLeaveConfirmation = false },
            title = { Text("Leave Group") },
            text = { Text("Are you sure you want to leave this group?") },
            confirmButton = {
                Button(
                    onClick = {
                        viewModel.leaveFamilyGroup(group.groupId, currentUserId)
                        showLeaveConfirmation = false
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Red) // Using red for emphasis
                ) {
                    Text("Leave")
                }
            },
            dismissButton = {
                Button(onClick = { showLeaveConfirmation = false }) {
                    Text("Cancel")
                }
            }
        )
    }
}

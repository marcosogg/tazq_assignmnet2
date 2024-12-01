package ie.setu.tazq.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ie.setu.tazq.data.Task
import ie.setu.tazq.ui.components.general.Centre
import ie.setu.tazq.ui.components.tasklist.TaskList
import ie.setu.tazq.ui.components.tasklist.TaskListHeader
import ie.setu.tazq.R

@Composable
fun TaskListScreen(
    modifier: Modifier = Modifier,
    tasks: SnapshotStateList<Task>
) {
    Column {
        Column(
            modifier = modifier.padding(
                top = 48.dp,
                start = 24.dp,
                end = 24.dp
            ),
        ) {
            if (tasks.isEmpty()) {
                Centre(Modifier.fillMaxSize()) {
                    Text(
                        color = MaterialTheme.colorScheme.secondary,
                        fontWeight = FontWeight.Bold,
                        fontSize = 30.sp,
                        lineHeight = 34.sp,
                        textAlign = TextAlign.Center,
                        text = stringResource(R.string.empty_list)
                    )
                }
            } else {
                TaskListHeader()
                TaskList(tasks = tasks)
            }
        }
    }
}


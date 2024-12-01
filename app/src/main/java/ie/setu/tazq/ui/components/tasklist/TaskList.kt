package ie.setu.tazq.ui.components.tasklist

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import ie.setu.tazq.data.Task
import java.text.DateFormat

@Composable
internal fun TaskList(
    tasks: SnapshotStateList<Task>,
    modifier: Modifier = Modifier
) {
    LazyColumn {
        items(
            items = tasks,
            key = { task -> task.id }
        ) { task ->
            TaskCard(
                title = task.title,
                priority = task.priority,
                description = task.description,
                dateCreated = DateFormat.getDateTimeInstance().format(task.dateCreated)
            )
        }
    }
}

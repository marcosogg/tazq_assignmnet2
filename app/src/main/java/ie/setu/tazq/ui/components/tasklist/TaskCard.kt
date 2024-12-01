package ie.setu.tazq.ui.components.tasklist

import android.text.format.DateFormat
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import ie.setu.tazq.data.TaskPriority
import java.util.*

@Composable
fun TaskCard(
    title: String,
    priority: TaskPriority,
    description: String,
    dateCreated: String
) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        ),
        modifier = Modifier.padding(vertical = 4.dp, horizontal = 2.dp)
    ) {
        TaskCardContent(title, priority, description, dateCreated)
    }
}

@Composable
private fun TaskCardContent(
    title: String,
    priority: TaskPriority,
    description: String,
    dateCreated: String
) {
    var expanded by remember { mutableStateOf(false) }

    Row(
        modifier = Modifier
            .padding(12.dp)
            .animateContentSize(
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioMediumBouncy,
                    stiffness = Spring.StiffnessLow
                )
            )
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(4.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = when(priority) {
                        TaskPriority.HIGH -> Icons.Filled.PriorityHigh
                        TaskPriority.MEDIUM -> Icons.Filled.Assignment
                        TaskPriority.LOW -> Icons.Filled.LowPriority
                    },
                    "Task Priority",
                    Modifier.padding(end = 8.dp)
                )
                Text(
                    text = title,
                    style = MaterialTheme.typography.headlineMedium.copy(
                        fontWeight = FontWeight.ExtraBold
                    )
                )
            }
            Text(
                text = "Created: $dateCreated",
                style = MaterialTheme.typography.labelSmall
            )
            if (expanded) {
                Text(
                    modifier = Modifier.padding(vertical = 16.dp),
                    text = description
                )
            }
        }
        IconButton(onClick = { expanded = !expanded }) {
            Icon(
                imageVector = if (expanded) Icons.Filled.ExpandLess else Icons.Filled.ExpandMore,
                contentDescription = if (expanded) "Show Less" else "Show More"
            )
        }
    }
}

package ie.setu.tazq.ui.components.task

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ie.setu.tazq.R

@Composable
fun RadioButtonGroup(
    modifier: Modifier = Modifier,
    onPriorityChange: (String) -> Unit
) {
    val priorityOptions = listOf(
        stringResource(R.string.high_priority),
        stringResource(R.string.low_priority)
    )
    var priority by remember { mutableStateOf(priorityOptions[0]) }

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(2.dp)
    ){
        priorityOptions.forEach { priorityText ->
            Row(verticalAlignment = Alignment.CenterVertically) {
                RadioButton(
                    selected = (priorityText == priority),
                    onClick = {
                        priority = priorityText
                        onPriorityChange(priority)
                    }
                )
                Text(
                    text = priorityText,
                    fontSize = 20.sp,
                    modifier = Modifier.padding(start = 8.dp)
                )
            }
        }
    }
}


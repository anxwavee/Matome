package com.example.matome.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.example.matome.data.Course
import com.example.matome.data.SampleData
import com.example.matome.data.Task
import com.example.matome.ui.theme.MatomeTheme
import com.example.matome.ui.theme.Spacing
import com.example.matome.ui.theme.accent

private val CourseBarWidth = 6.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskCard(
    task: Task,
    course: Course?,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val courseColor = course?.color?.accent() ?: MaterialTheme.colorScheme.outline

    Card(onClick = onClick, modifier = modifier.fillMaxWidth()) {
        Row(modifier = Modifier.height(IntrinsicSize.Min)) {
            Box(
                modifier = Modifier
                    .width(CourseBarWidth)
                    .fillMaxHeight()
                    .background(courseColor)
            )
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(Spacing.m),
                verticalArrangement = Arrangement.spacedBy(Spacing.xs),
            ) {
                Text(
                    text = course?.name ?: "No course",
                    style = MaterialTheme.typography.labelMedium,
                    color = courseColor,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
                Text(
                    text = task.title,
                    style = MaterialTheme.typography.titleMedium,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    textDecoration = if (task.isCompleted) TextDecoration.LineThrough else null,
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        text = "Deadline: ${task.deadline}",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.weight(1f),
                    )
                    Text(
                        text = task.timeLeft,
                        style = MaterialTheme.typography.labelMedium,
                        color = if (task.isUrgent && !task.isCompleted) {
                            MaterialTheme.colorScheme.error
                        } else {
                            MaterialTheme.colorScheme.onSurfaceVariant
                        },
                    )
                }
            }
            val iconModifier = Modifier.padding(top = Spacing.m, end = Spacing.m)
            when {
                task.isCompleted -> Icon(
                    imageVector = Icons.Filled.CheckCircle,
                    contentDescription = "Completed",
                    tint = MaterialTheme.colorScheme.secondary,
                    modifier = iconModifier,
                )
                task.isUrgent -> Icon(
                    imageVector = Icons.Filled.Warning,
                    contentDescription = "Urgent",
                    tint = MaterialTheme.colorScheme.error,
                    modifier = iconModifier,
                )
            }
        }
    }
}

@PreviewLightDark
@Composable
private fun TaskCardPreview() {
    MatomeTheme {
        Surface {
            Column(
                modifier = Modifier.padding(Spacing.m),
                verticalArrangement = Arrangement.spacedBy(Spacing.s),
            ) {
                TaskCard(task = SampleData.tasks[0], course = SampleData.courses[1], onClick = {})
                TaskCard(task = SampleData.tasks[4], course = SampleData.courses[1], onClick = {})
                TaskCard(task = SampleData.tasks[11], course = SampleData.courses[2], onClick = {})
            }
        }
    }
}

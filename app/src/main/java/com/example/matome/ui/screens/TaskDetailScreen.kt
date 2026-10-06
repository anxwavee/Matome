package com.example.matome.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.example.matome.data.Course
import com.example.matome.data.SampleData
import com.example.matome.data.Task
import com.example.matome.ui.components.CourseCover
import com.example.matome.ui.components.EmptyState
import com.example.matome.ui.components.InfoRow
import com.example.matome.ui.components.MatomeTopBar
import com.example.matome.ui.theme.MatomeTheme
import com.example.matome.ui.theme.Spacing
import com.example.matome.ui.theme.accent

private val CoverHeight = 120.dp

@Composable
fun TaskDetailScreen(
    taskId: Int,
    onBackClick: () -> Unit,
    onCourseClick: (courseId: Int) -> Unit,
    modifier: Modifier = Modifier,
    tasks: List<Task> = SampleData.tasks,
    courses: List<Course> = SampleData.courses,
) {
    val task = tasks.find { it.id == taskId }
    val course = courses.find { it.id == task?.courseId }

    Scaffold(
        modifier = modifier,
        topBar = { MatomeTopBar(title = "Task details", onBackClick = onBackClick) },
    ) { innerPadding ->
        if (task == null) {
            EmptyState(
                title = "Task not found",
                message = "It may have been deleted.",
                modifier = Modifier.padding(innerPadding),
            )
        } else {
            TaskDetailContent(
                task = task,
                course = course,
                onCourseClick = onCourseClick,
                modifier = Modifier.padding(innerPadding),
            )
        }
    }
}

/**
 * Column (scrollable)
 * ├─ CourseCover
 * ├─ Text course name, Text title, "Urgent" Row
 * ├─ Text description
 * ├─ InfoRow × 4
 * └─ Button "Mark as done", OutlinedButton "Open course"
 */
@Composable
private fun TaskDetailContent(
    task: Task,
    course: Course?,
    onCourseClick: (courseId: Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    // State that lives only while this screen is open.
    // Saving it for real comes in phase 2 (Room).
    var isDone by remember { mutableStateOf(task.isCompleted) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(Spacing.m),
        verticalArrangement = Arrangement.spacedBy(Spacing.m),
    ) {
        if (course != null) {
            CourseCover(
                course = course,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(CoverHeight)
                    .clip(MaterialTheme.shapes.large),
            )
        }

        Column(verticalArrangement = Arrangement.spacedBy(Spacing.xs)) {
            Text(
                text = course?.name ?: "No course",
                style = MaterialTheme.typography.labelLarge,
                color = course?.color?.accent() ?: MaterialTheme.colorScheme.onSurfaceVariant,
            )
            Text(text = task.title, style = MaterialTheme.typography.headlineSmall)
            if (task.isUrgent && !isDone) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(Spacing.xs),
                ) {
                    Icon(
                        imageVector = Icons.Filled.Warning,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.error,
                    )
                    Text(
                        text = "Urgent",
                        style = MaterialTheme.typography.labelLarge,
                        color = MaterialTheme.colorScheme.error,
                    )
                }
            }
        }

        Text(text = task.description, style = MaterialTheme.typography.bodyLarge)

        InfoRow(icon = Icons.Filled.DateRange, label = "Deadline", value = task.deadline)
        InfoRow(
            icon = Icons.Filled.Info,
            label = "Time left",
            value = if (isDone) "Completed" else task.timeLeft,
        )
        if (course != null) {
            InfoRow(icon = Icons.Filled.Person, label = "Teacher", value = course.teacher)
            InfoRow(icon = Icons.Filled.LocationOn, label = "Room", value = course.room)
        }

        Button(
            onClick = { isDone = !isDone },
            modifier = Modifier.fillMaxWidth(),
        ) {
            if (!isDone) {
                Icon(imageVector = Icons.Filled.Check, contentDescription = null)
                Spacer(modifier = Modifier.width(Spacing.s))
            }
            Text(text = if (isDone) "Mark as not done" else "Mark as done")
        }
        if (course != null) {
            OutlinedButton(
                onClick = { onCourseClick(course.id) },
                modifier = Modifier.fillMaxWidth(),
            ) {
                Text(
                    text = "Open ${course.name}",
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
            }
        }
    }
}

@PreviewLightDark
@Composable
private fun TaskDetailScreenPreview() {
    MatomeTheme {
        TaskDetailScreen(taskId = 1, onBackClick = {}, onCourseClick = {})
    }
}

@PreviewLightDark
@Composable
private fun TaskDetailScreenNotFoundPreview() {
    MatomeTheme {
        TaskDetailScreen(taskId = 999, onBackClick = {}, onCourseClick = {})
    }
}

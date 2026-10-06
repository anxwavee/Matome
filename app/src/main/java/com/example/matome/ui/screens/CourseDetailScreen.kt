package com.example.matome.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.example.matome.data.Course
import com.example.matome.data.SampleData
import com.example.matome.data.Task
import com.example.matome.ui.components.CourseCover
import com.example.matome.ui.components.EmptyState
import com.example.matome.ui.components.InfoRow
import com.example.matome.ui.components.MatomeTopBar
import com.example.matome.ui.components.SectionHeader
import com.example.matome.ui.components.TaskCard
import com.example.matome.ui.theme.MatomeTheme
import com.example.matome.ui.theme.Spacing
import kotlin.math.roundToInt

private val CoverHeight = 140.dp

@Composable
fun CourseDetailScreen(
    courseId: Int,
    onBackClick: () -> Unit,
    onTaskClick: (taskId: Int) -> Unit,
    modifier: Modifier = Modifier,
    courses: List<Course> = SampleData.courses,
    tasks: List<Task> = SampleData.tasks,
) {
    val course = courses.find { it.id == courseId }
    val courseTasks = tasks.filter { it.courseId == courseId }

    Scaffold(
        modifier = modifier,
        topBar = {
            MatomeTopBar(title = course?.name ?: "Course", onBackClick = onBackClick)
        },
    ) { innerPadding ->
        if (course == null) {
            EmptyState(
                title = "Course not found",
                message = "It may have been deleted.",
                modifier = Modifier.padding(innerPadding),
            )
        } else {
            CourseDetailContent(
                course = course,
                courseTasks = courseTasks,
                onTaskClick = onTaskClick,
                modifier = Modifier.padding(innerPadding),
            )
        }
    }
}

@Composable
private fun CourseDetailContent(
    course: Course,
    courseTasks: List<Task>,
    onTaskClick: (taskId: Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(Spacing.m),
        verticalArrangement = Arrangement.spacedBy(Spacing.m),
    ) {
        item {
            CourseCover(
                course = course,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(CoverHeight)
                    .clip(MaterialTheme.shapes.large),
            )
        }
        item { InfoRow(icon = Icons.Filled.Person, label = "Teacher", value = course.teacher) }
        item { InfoRow(icon = Icons.Filled.LocationOn, label = "Room", value = course.room) }
        item {
            InfoRow(
                icon = Icons.Filled.CheckCircle,
                label = "Progress",
                value = "${(course.progress * 100).roundToInt()}%",
            )
        }
        item { SectionHeader(title = "Tasks") }
        if (courseTasks.isEmpty()) {
            item {
                EmptyState(
                    title = "No tasks yet",
                    message = "Tasks you add for this course will show up here.",
                )
            }
        } else {
            items(courseTasks) { task ->
                TaskCard(task = task, course = course, onClick = { onTaskClick(task.id) })
            }
        }
    }
}

@PreviewLightDark
@Composable
private fun CourseDetailScreenPreview() {
    MatomeTheme {
        CourseDetailScreen(courseId = 1, onBackClick = {}, onTaskClick = {})
    }
}

@PreviewLightDark
@Composable
private fun CourseDetailScreenEmptyPreview() {
    // Course 4 (Databases) has no tasks → shows the empty state.
    MatomeTheme {
        CourseDetailScreen(courseId = 4, onBackClick = {}, onTaskClick = {})
    }
}

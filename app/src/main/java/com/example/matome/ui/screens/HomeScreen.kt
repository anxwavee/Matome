package com.example.matome.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.example.matome.data.Course
import com.example.matome.data.SampleData
import com.example.matome.data.Task
import com.example.matome.navigation.Routes
import com.example.matome.ui.components.ActivityHeatmap
import com.example.matome.ui.components.CourseCard
import com.example.matome.ui.components.EmptyState
import com.example.matome.ui.components.MatomeBottomBar
import com.example.matome.ui.components.MatomeTopBar
import com.example.matome.ui.components.SectionHeader
import com.example.matome.ui.components.TaskCard
import com.example.matome.ui.theme.MatomeTheme
import com.example.matome.ui.theme.Spacing

@Composable
fun HomeScreen(
    onTaskClick: (taskId: Int) -> Unit,
    onCourseClick: (courseId: Int) -> Unit,
    onSeeAllClick: () -> Unit,
    onBottomNavClick: (route: String) -> Unit,
    modifier: Modifier = Modifier,
    userName: String = SampleData.USER_NAME,
    courses: List<Course> = SampleData.courses,
    tasks: List<Task> = SampleData.tasks,
    activity: List<Int> = SampleData.activity,
) {
    val openTasks = tasks.filter { !it.isCompleted }
    val todayUrgent = openTasks.filter { it.isDueToday && it.isUrgent }
    val todayOther = openTasks.filter { it.isDueToday && !it.isUrgent }
    val thisWeek = openTasks.filter { !it.isDueToday }

    Scaffold(
        modifier = modifier,
        topBar = {
            MatomeTopBar(
                title = "Welcome, $userName",
                actions = {
                    IconButton(onClick = {}) {
                        Icon(
                            imageVector = Icons.Filled.Notifications,
                            contentDescription = "Notifications",
                        )
                    }
                },
            )
        },
        bottomBar = {
            MatomeBottomBar(currentRoute = Routes.HOME, onItemClick = onBottomNavClick)
        },
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentPadding = PaddingValues(Spacing.m),
            verticalArrangement = Arrangement.spacedBy(Spacing.s),
        ) {
            item { SectionHeader(title = "Your activity") }
            item { ActivityHeatmap(dailyCounts = activity) }

            item { SectionHeader(title = "Courses") }
            item {
                if (courses.isEmpty()) {
                    EmptyState(title = "No courses yet", message = "Add your first course to get started.")
                } else {
                    LazyRow(horizontalArrangement = Arrangement.spacedBy(Spacing.s)) {
                        items(courses) { course ->
                            CourseCard(course = course, onClick = { onCourseClick(course.id) })
                        }
                    }
                }
            }

            item {
                SectionHeader(
                    title = "Today's tasks",
                    actionLabel = "See all",
                    onActionClick = onSeeAllClick,
                )
            }
            if (todayUrgent.isEmpty() && todayOther.isEmpty()) {
                item {
                    EmptyState(
                        title = "Nothing due today",
                        message = "Enjoy the break, or get ahead on this week's tasks.",
                    )
                }
            }
            if (todayUrgent.isNotEmpty()) {
                item { GroupLabel(text = "Urgent", color = MaterialTheme.colorScheme.error) }
                items(todayUrgent) { task ->
                    TaskCard(
                        task = task,
                        course = courses.find { it.id == task.courseId },
                        onClick = { onTaskClick(task.id) },
                    )
                }
            }
            if (todayOther.isNotEmpty()) {
                item { GroupLabel(text = "Later today", color = MaterialTheme.colorScheme.onSurfaceVariant) }
                items(todayOther) { task ->
                    TaskCard(
                        task = task,
                        course = courses.find { it.id == task.courseId },
                        onClick = { onTaskClick(task.id) },
                    )
                }
            }

            item { SectionHeader(title = "This week") }
            if (thisWeek.isEmpty()) {
                item {
                    EmptyState(title = "Your week is clear", message = "No other deadlines coming up.")
                }
            } else {
                items(thisWeek) { task ->
                    TaskCard(
                        task = task,
                        course = courses.find { it.id == task.courseId },
                        onClick = { onTaskClick(task.id) },
                    )
                }
            }
        }
    }
}

@Composable
private fun GroupLabel(text: String, color: Color) {
    Text(text = text, style = MaterialTheme.typography.labelLarge, color = color)
}

@PreviewLightDark
@Composable
private fun HomeScreenPreview() {
    MatomeTheme {
        HomeScreen(onTaskClick = {}, onCourseClick = {}, onSeeAllClick = {}, onBottomNavClick = {})
    }
}

@PreviewLightDark
@Composable
private fun HomeScreenEmptyPreview() {
    MatomeTheme {
        HomeScreen(
            onTaskClick = {},
            onCourseClick = {},
            onSeeAllClick = {},
            onBottomNavClick = {},
            tasks = emptyList(),
        )
    }
}

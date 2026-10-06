package com.example.matome.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.example.matome.data.Course
import com.example.matome.data.SampleData
import com.example.matome.data.Task
import com.example.matome.navigation.Routes
import com.example.matome.ui.components.EmptyState
import com.example.matome.ui.components.MatomeBottomBar
import com.example.matome.ui.components.MatomeTopBar
import com.example.matome.ui.components.TaskCard
import com.example.matome.ui.theme.MatomeTheme
import com.example.matome.ui.theme.Spacing

enum class TaskFilter(val label: String) {
    All("All"),
    Urgent("Urgent"),
    Today("Today"),
    Done("Done"),
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TasksScreen(
    onTaskClick: (taskId: Int) -> Unit,
    onBackClick: () -> Unit,
    onBottomNavClick: (route: String) -> Unit,
    modifier: Modifier = Modifier,
    tasks: List<Task> = SampleData.tasks,
    courses: List<Course> = SampleData.courses,
) {
    var selectedFilter by remember { mutableStateOf(TaskFilter.All) }

    val visibleTasks = when (selectedFilter) {
        TaskFilter.All -> tasks
        TaskFilter.Urgent -> tasks.filter { it.isUrgent && !it.isCompleted }
        TaskFilter.Today -> tasks.filter { it.isDueToday && !it.isCompleted }
        TaskFilter.Done -> tasks.filter { it.isCompleted }
    }

    Scaffold(
        modifier = modifier,
        topBar = { MatomeTopBar(title = "All tasks", onBackClick = onBackClick) },
        bottomBar = {
            MatomeBottomBar(currentRoute = Routes.TASKS, onItemClick = onBottomNavClick)
        },
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
        ) {
            LazyRow(
                contentPadding = PaddingValues(horizontal = Spacing.m),
                horizontalArrangement = Arrangement.spacedBy(Spacing.s),
            ) {
                items(TaskFilter.entries) { filter ->
                    FilterChip(
                        selected = filter == selectedFilter,
                        onClick = { selectedFilter = filter },
                        label = { Text(text = filter.label) },
                    )
                }
            }

            if (visibleTasks.isEmpty()) {
                EmptyState(
                    title = "No tasks here",
                    message = "Nothing matches the \"${selectedFilter.label}\" filter.",
                )
            } else {
                LazyColumn(
                    contentPadding = PaddingValues(Spacing.m),
                    verticalArrangement = Arrangement.spacedBy(Spacing.s),
                ) {
                    items(visibleTasks) { task ->
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
}

@PreviewLightDark
@Composable
private fun TasksScreenPreview() {
    MatomeTheme {
        TasksScreen(onTaskClick = {}, onBackClick = {}, onBottomNavClick = {})
    }
}

@PreviewLightDark
@Composable
private fun TasksScreenEmptyPreview() {
    MatomeTheme {
        TasksScreen(onTaskClick = {}, onBackClick = {}, onBottomNavClick = {}, tasks = emptyList())
    }
}

package com.example.matome.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.matome.data.SampleData
import com.example.matome.navigation.Routes
import com.example.matome.ui.components.EmptyState
import com.example.matome.ui.components.MatomeBottomBar
import com.example.matome.ui.components.MatomeTopBar
import com.example.matome.ui.components.TaskCard
import com.example.matome.ui.theme.MatomeTheme
import com.example.matome.ui.theme.Spacing

@Composable
fun TasksRoute(
    onTaskClick: (taskId: Int) -> Unit,
    onBackClick: () -> Unit,
    onBottomNavClick: (route: String) -> Unit,
    viewModel: TasksViewModel = viewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    TasksScreen(
        uiState = uiState,
        onSearchChange = viewModel::onSearchChange,
        onFilterSelected = viewModel::onFilterSelected,
        onToggleCompleted = viewModel::onToggleCompleted,
        onRetry = viewModel::onRetry,
        onTaskClick = onTaskClick,
        onBackClick = onBackClick,
        onBottomNavClick = onBottomNavClick,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TasksScreen(
    uiState: TasksUiState,
    onSearchChange: (String) -> Unit,
    onFilterSelected: (TaskFilter) -> Unit,
    onToggleCompleted: (taskId: Int) -> Unit,
    onRetry: () -> Unit,
    onTaskClick: (taskId: Int) -> Unit,
    onBackClick: () -> Unit,
    onBottomNavClick: (route: String) -> Unit,
    modifier: Modifier = Modifier,
) {
    Scaffold(
        modifier = modifier,
        topBar = { MatomeTopBar(title = "All tasks", onBackClick = onBackClick) },
        bottomBar = { MatomeBottomBar(currentRoute = Routes.TASKS, onItemClick = onBottomNavClick) },
    ) { innerPadding ->
        Column(modifier = Modifier.fillMaxSize().padding(innerPadding)) {

            OutlinedTextField(
                value = uiState.searchQuery,
                onValueChange = onSearchChange,
                label = { Text("Search tasks") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth().padding(horizontal = Spacing.m),
            )

            LazyRow(
                contentPadding = PaddingValues(horizontal = Spacing.m, vertical = Spacing.s),
                horizontalArrangement = Arrangement.spacedBy(Spacing.s),
            ) {
                items(TaskFilter.entries) { filter ->
                    FilterChip(
                        selected = filter == uiState.selectedFilter,
                        onClick = { onFilterSelected(filter) },
                        label = { Text(filter.label) },
                    )
                }
            }

            when {
                uiState.isLoading -> Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }

                uiState.error != null -> Column(
                    modifier = Modifier.fillMaxSize().padding(Spacing.m),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                ) {
                    Text(uiState.error, color = MaterialTheme.colorScheme.error)
                    Button(onClick = onRetry, modifier = Modifier.padding(top = Spacing.s)) {
                        Text("Retry")
                    }
                }

                uiState.tasks.isEmpty() -> EmptyState(
                    title = "No tasks here",
                    message = "Nothing matches your search or the \"${uiState.selectedFilter.label}\" filter.",
                )

                else -> LazyColumn(
                    contentPadding = PaddingValues(Spacing.m),
                    verticalArrangement = Arrangement.spacedBy(Spacing.s),
                ) {
                    items(uiState.tasks, key = { it.id }) { task ->
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Checkbox(
                                checked = task.isCompleted,
                                onCheckedChange = { onToggleCompleted(task.id) },
                            )
                            TaskCard(
                                task = task,
                                course = uiState.courses.find { it.id == task.courseId },
                                onClick = { onTaskClick(task.id) },
                                modifier = Modifier.weight(1f),
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun PreviewTasks(state: TasksUiState) {
    MatomeTheme {
        TasksScreen(
            uiState = state,
            onSearchChange = {}, onFilterSelected = {}, onToggleCompleted = {}, onRetry = {},
            onTaskClick = {}, onBackClick = {}, onBottomNavClick = {},
        )
    }
}

@Preview(showBackground = true, name = "Loading")
@Composable
private fun TasksLoadingPreview() = PreviewTasks(TasksUiState(isLoading = true))

@Preview(showBackground = true, name = "Content")
@Composable
private fun TasksContentPreview() = PreviewTasks(
    TasksUiState(tasks = SampleData.tasks, courses = SampleData.courses)
)

@Preview(showBackground = true, name = "Empty")
@Composable
private fun TasksEmptyPreview() = PreviewTasks(
    TasksUiState(searchQuery = "xyz", courses = SampleData.courses)
)

@Preview(showBackground = true, name = "Error")
@Composable
private fun TasksErrorPreview() = PreviewTasks(
    TasksUiState(error = "Couldn't load tasks. Try again.")
)
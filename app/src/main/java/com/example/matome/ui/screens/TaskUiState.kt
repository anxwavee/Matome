package com.example.matome.ui.screens

import com.example.matome.data.Course
import com.example.matome.data.Task

enum class TaskFilter(val label: String) {
    All("All"),
    Urgent("Urgent"),
    Today("Today"),
    Done("Done"),
}

data class TasksUiState(
    val tasks: List<Task> = emptyList(),
    val courses: List<Course> = emptyList(),
    val searchQuery: String = "",
    val selectedFilter: TaskFilter = TaskFilter.All,
    val isLoading: Boolean = false,
    val error: String? = null,
)
package com.example.matome.ui.screens

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.matome.data.Task
import com.example.matome.data.TaskRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class TasksViewModel(
    private val repository: TaskRepository = TaskRepository(),
) : ViewModel() {
    private val _uiState = MutableStateFlow(TasksUiState(isLoading = true))

    val uiState: StateFlow<TasksUiState> = _uiState.asStateFlow()

    private var allTasks: List<Task> = emptyList()

    init {
        Log.d("TasksViewModel", "init")
        loadTasks()
    }

    private fun loadTasks() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }
            try {
                val courses = repository.getCourses()
                allTasks = repository.getTasks()
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        courses = courses,
                        tasks = applyFilters(allTasks, it.searchQuery, it.selectedFilter),
                    )
                }
            } catch (e: Exception) {
                _uiState.update { it.copy(isLoading = false, error = e.message ?: "Something went wrong") }
            }
        }
    }

    fun onSearchChange(query: String) {
        _uiState.update {
            it.copy(searchQuery = query, tasks = applyFilters(allTasks, query, it.selectedFilter))
        }
    }

    fun onFilterSelected(filter: TaskFilter) {
        _uiState.update {
            it.copy(selectedFilter = filter, tasks = applyFilters(allTasks, it.searchQuery, filter))
        }
    }

    fun onToggleCompleted(taskId: Int) {
        repository.toggleCompleted(taskId)
        allTasks = allTasks.map { if (it.id == taskId) it.copy(isCompleted = !it.isCompleted) else it }
        _uiState.update {
            it.copy(tasks = applyFilters(allTasks, it.searchQuery, it.selectedFilter))
        }
    }

    fun onRetry() {
        repository.shouldFail = false
        loadTasks()
    }

    private fun applyFilters(tasks: List<Task>, query: String, filter: TaskFilter): List<Task> {
        val byFilter = when (filter) {
            TaskFilter.All -> tasks
            TaskFilter.Urgent -> tasks.filter { it.isUrgent && !it.isCompleted }
            TaskFilter.Today -> tasks.filter { it.isDueToday && !it.isCompleted }
            TaskFilter.Done -> tasks.filter { it.isCompleted }
        }
        if (query.isBlank()) return byFilter
        return byFilter.filter {
            it.title.contains(query, ignoreCase = true) ||
                    it.description.contains(query, ignoreCase = true)
        }
    }

    override fun onCleared() {
        Log.d("TasksViewModel", "onCleared")
        super.onCleared()
    }
}
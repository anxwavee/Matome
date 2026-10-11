package com.example.matome.data

import kotlinx.coroutines.delay

class TaskRepository {

    // later set to true to test the error state
    var shouldFail = false
    private val tasks = SampleData.tasks.toMutableList()

    suspend fun getTasks(): List<Task> {
        delay(1000)
        if (shouldFail) {
            throw IllegalStateException("Couldn't load tasks. Try again.")
        }
        return tasks.toList()
    }

    suspend fun getCourses(): List<Course> {
        delay(300)
        return SampleData.courses
    }

    fun toggleCompleted(taskId: Int) {
        val index = tasks.indexOfFirst { it.id == taskId }
        if (index != -1) {
            val task = tasks[index]
            tasks[index] = task.copy(isCompleted = !task.isCompleted)
        }
    }
}
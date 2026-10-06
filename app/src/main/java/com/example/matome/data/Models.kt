package com.example.matome.data

import androidx.annotation.DrawableRes

enum class CourseColor { Blue, Green, Yellow }

data class Course(
    val id: Int,
    val name: String,
    val teacher: String,
    val room: String,
    val progress: Float,
    val color: CourseColor,
    @DrawableRes val coverRes: Int,
)

data class Task(
    val id: Int,
    val courseId: Int,
    val title: String,
    val description: String,
    val deadline: String,
    val timeLeft: String,
    val isUrgent: Boolean,
    val isDueToday: Boolean,
    val isCompleted: Boolean = false,
)

package com.example.matome.data

import com.example.matome.R
import kotlin.random.Random

object SampleData {

    const val USER_NAME = "Student"

    val courses = listOf(
        Course(
            id = 1,
            name = "Android Development",
            teacher = "A. Serikbayev",
            room = "Room 402",
            progress = 0.6f,
            color = CourseColor.Blue,
            coverRes = R.drawable.cover_android,
        ),
        Course(
            id = 2,
            name = "Machine Learning",
            teacher = "D. Nurlanova",
            room = "Room 215",
            progress = 0.3f,
            color = CourseColor.Green,
            coverRes = R.drawable.cover_ml,
        ),
        Course(
            id = 3,
            name = "Algorithms and Data Structures",
            teacher = "M. Omarov",
            room = "Room 118",
            progress = 0.8f,
            color = CourseColor.Yellow,
            coverRes = R.drawable.cover_algorithms,
        ),
        // No tasks yet -> its course screen shows the empty state.
        Course(
            id = 4,
            name = "Databases",
            teacher = "K. Akhmetova",
            room = "Room 305",
            progress = 0.1f,
            color = CourseColor.Blue,
            coverRes = R.drawable.cover_databases,
        ),
    )

    val tasks = listOf(
        Task(1, 2, "Finish linear regression notebook",
            "Complete the last two exercises and plot the loss curve.",
            deadline = "29.09", timeLeft = "3 h left", isUrgent = true, isDueToday = true),
        Task(2, 1, "Build the home screen for SIS3",
            "Scaffold, LazyColumn, course row and task cards.",
            deadline = "29.09", timeLeft = "7 h left", isUrgent = true, isDueToday = true),
        Task(3, 3, "Solve 5 graph problems",
            "BFS, DFS and one shortest-path problem.",
            deadline = "29.09", timeLeft = "9 h left", isUrgent = false, isDueToday = true),
        Task(4, 1, "Read about Navigation Compose arguments",
            "How an id is passed to the detail screen.",
            deadline = "30.09", timeLeft = "1 day left", isUrgent = false, isDueToday = false),
        // Long title on purpose: shows the ellipsis (…) in the task card.
        Task(5, 2, "Watch lecture 4: gradient descent and learning rate schedules explained step by step",
            "Take notes on learning rate and convergence.",
            deadline = "01.10", timeLeft = "2 days left", isUrgent = false, isDueToday = false),
        Task(6, 3, "Quiz: sorting algorithms",
            "Merge sort, quick sort, heap sort and their complexity.",
            deadline = "01.10", timeLeft = "2 days left", isUrgent = true, isDueToday = false),
        Task(7, 2, "Prepare dataset for the project",
            "Clean the CSV and split it into train and test sets.",
            deadline = "03.10", timeLeft = "4 days left", isUrgent = false, isDueToday = false),
        Task(8, 3, "Midterm revision: trees and heaps",
            "Go through lectures 5–7 and past midterm questions.",
            deadline = "05.10", timeLeft = "6 days left", isUrgent = true, isDueToday = false),
        Task(9, 1, "Write README with screenshots",
            "Light and dark screenshots of every screen.",
            deadline = "05.10", timeLeft = "6 days left", isUrgent = false, isDueToday = false),
        Task(10, 1, "Submit SIS3 repository link",
            "Post the GitHub link in the Teams assignment.",
            deadline = "06.10", timeLeft = "7 days left", isUrgent = false, isDueToday = false),
        Task(11, 2, "Read paper summary",
            "One-page summary of the assigned paper.",
            deadline = "28.09", timeLeft = "Done", isUrgent = false, isDueToday = false, isCompleted = true),
        Task(12, 3, "Homework 2: recursion",
            "Five recursion problems with explanations.",
            deadline = "27.09", timeLeft = "Done", isUrgent = false, isDueToday = false, isCompleted = true),
    )

    val activity: List<Int> = Random(seed = 2026).let { random ->
        List(84) { if (random.nextInt(10) < 3) 0 else random.nextInt(1, 5) }
    }
}

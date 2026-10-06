package com.example.matome.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.matome.ui.screens.CourseDetailScreen
import com.example.matome.ui.screens.HomeScreen
import com.example.matome.ui.screens.SettingsScreen
import com.example.matome.ui.screens.TaskDetailScreen
import com.example.matome.ui.screens.TasksScreen

object Routes {
    const val HOME = "home"
    const val TASKS = "tasks"
    const val SETTINGS = "settings"
    const val TASK_DETAIL = "task/{taskId}"
    const val COURSE_DETAIL = "course/{courseId}"

    fun taskDetail(taskId: Int) = "task/$taskId"
    fun courseDetail(courseId: Int) = "course/$courseId"
}

@Composable
fun MatomeNavHost(
    navController: NavHostController = rememberNavController(),
) {
    NavHost(navController = navController, startDestination = Routes.HOME) {

        composable(Routes.HOME) {
            HomeScreen(
                onTaskClick = { taskId -> navController.navigate(Routes.taskDetail(taskId)) },
                onCourseClick = { courseId -> navController.navigate(Routes.courseDetail(courseId)) },
                onSeeAllClick = { navController.navigateToSection(Routes.TASKS) },
                onBottomNavClick = { route -> navController.navigateToSection(route) },
            )
        }

        composable(Routes.TASKS) {
            TasksScreen(
                onTaskClick = { taskId -> navController.navigate(Routes.taskDetail(taskId)) },
                onBackClick = { navController.popBackStack() },
                onBottomNavClick = { route -> navController.navigateToSection(route) },
            )
        }

        composable(Routes.SETTINGS) {
            SettingsScreen(
                onBackClick = { navController.popBackStack() },
                onBottomNavClick = { route -> navController.navigateToSection(route) },
            )
        }

        composable(
            route = Routes.TASK_DETAIL,
            arguments = listOf(navArgument("taskId") { type = NavType.IntType }),
        ) { backStackEntry ->
            // Read the id out of the route "task/5" -> 5
            val taskId = backStackEntry.arguments?.getInt("taskId") ?: -1
            TaskDetailScreen(
                taskId = taskId,
                onBackClick = { navController.popBackStack() },
                onCourseClick = { courseId -> navController.navigate(Routes.courseDetail(courseId)) },
            )
        }

        composable(
            route = Routes.COURSE_DETAIL,
            arguments = listOf(navArgument("courseId") { type = NavType.IntType }),
        ) { backStackEntry ->
            val courseId = backStackEntry.arguments?.getInt("courseId") ?: -1
            CourseDetailScreen(
                courseId = courseId,
                onBackClick = { navController.popBackStack() },
                onTaskClick = { taskId -> navController.navigate(Routes.taskDetail(taskId)) },
            )
        }
    }
}

private fun NavHostController.navigateToSection(route: String) {
    navigate(route) {
        popUpTo(Routes.HOME)
        launchSingleTop = true
    }
}

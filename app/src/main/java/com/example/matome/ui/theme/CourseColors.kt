package com.example.matome.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.ui.graphics.Color
import com.example.matome.data.CourseColor

@Composable
@ReadOnlyComposable
fun CourseColor.accent(): Color = when (this) {
    CourseColor.Blue -> MaterialTheme.colorScheme.primary
    CourseColor.Green -> MaterialTheme.colorScheme.secondary
    CourseColor.Yellow -> MaterialTheme.colorScheme.tertiary
}

@Composable
@ReadOnlyComposable
fun CourseColor.onAccent(): Color = when (this) {
    CourseColor.Blue -> MaterialTheme.colorScheme.onPrimary
    CourseColor.Green -> MaterialTheme.colorScheme.onSecondary
    CourseColor.Yellow -> MaterialTheme.colorScheme.onTertiary
}

@Composable
@ReadOnlyComposable
fun CourseColor.container(): Color = when (this) {
    CourseColor.Blue -> MaterialTheme.colorScheme.primaryContainer
    CourseColor.Green -> MaterialTheme.colorScheme.secondaryContainer
    CourseColor.Yellow -> MaterialTheme.colorScheme.tertiaryContainer
}

@Composable
@ReadOnlyComposable
fun CourseColor.onContainer(): Color = when (this) {
    CourseColor.Blue -> MaterialTheme.colorScheme.onPrimaryContainer
    CourseColor.Green -> MaterialTheme.colorScheme.onSecondaryContainer
    CourseColor.Yellow -> MaterialTheme.colorScheme.onTertiaryContainer
}

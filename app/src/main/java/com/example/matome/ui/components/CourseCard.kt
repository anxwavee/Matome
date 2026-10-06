package com.example.matome.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.matome.data.Course
import com.example.matome.data.SampleData
import com.example.matome.ui.theme.MatomeTheme
import com.example.matome.ui.theme.Spacing
import com.example.matome.ui.theme.accent
import com.example.matome.ui.theme.container
import com.example.matome.ui.theme.onAccent
import com.example.matome.ui.theme.onContainer
import kotlin.math.roundToInt

private val CourseCardWidth = 200.dp
private val CoverHeight = 80.dp
private val CoverIconSize = 40.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CourseCard(
    course: Course,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Card(
        onClick = onClick,
        modifier = modifier.width(CourseCardWidth),
        colors = CardDefaults.cardColors(
            containerColor = course.color.container(),
            contentColor = course.color.onContainer(),
        ),
    ) {
        CourseCover(
            course = course,
            modifier = Modifier
                .fillMaxWidth()
                .height(CoverHeight),
        )
        Column(
            modifier = Modifier.padding(Spacing.m),
            verticalArrangement = Arrangement.spacedBy(Spacing.s),
        ) {
            Text(
                text = course.name,
                style = MaterialTheme.typography.titleMedium,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(Spacing.s),
            ) {
                LinearProgressIndicator(
                    progress = { course.progress },
                    modifier = Modifier.weight(1f),
                    color = course.color.accent(),
                    trackColor = MaterialTheme.colorScheme.surface,
                )
                Text(
                    text = "${(course.progress * 100).roundToInt()}%",
                    style = MaterialTheme.typography.labelMedium,
                )
            }
            Text(
                text = "${course.teacher} · ${course.room}",
                style = MaterialTheme.typography.bodySmall,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
        }
    }
}

/**
 * The course illustration: an Image from res/drawable on the course color.
 * Used on the course card and at the top of the Course and Task screens.
 */
@Composable
fun CourseCover(
    course: Course,
    modifier: Modifier = Modifier,
    iconSize: Dp = CoverIconSize,
) {
    Box(
        modifier = modifier.background(course.color.accent()),
        contentAlignment = Alignment.Center,
    ) {
        Image(
            painter = painterResource(id = course.coverRes),
            contentDescription = "Cover illustration for ${course.name}",
            modifier = Modifier.size(iconSize),
            colorFilter = ColorFilter.tint(course.color.onAccent()),
        )
    }
}

@PreviewLightDark
@Composable
private fun CourseCardPreview() {
    MatomeTheme {
        Surface {
            Row(
                modifier = Modifier.padding(Spacing.m),
                horizontalArrangement = Arrangement.spacedBy(Spacing.s),
            ) {
                CourseCard(course = SampleData.courses[0], onClick = {})
                CourseCard(course = SampleData.courses[2], onClick = {})
            }
        }
    }
}

@PreviewLightDark
@Composable
private fun CourseCoverPreview() {
    MatomeTheme {
        CourseCover(
            course = SampleData.courses[1],
            modifier = Modifier
                .width(CourseCardWidth)
                .height(CoverHeight),
        )
    }
}

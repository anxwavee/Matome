package com.example.matome.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.example.matome.data.SampleData
import com.example.matome.ui.theme.MatomeTheme
import com.example.matome.ui.theme.Spacing

private val CellSize = 16.dp
private val DayLabels = listOf("Mon", "", "Wed", "", "Fri", "", "")

@Composable
fun ActivityHeatmap(
    dailyCounts: List<Int>,
    modifier: Modifier = Modifier,
) {
    val weeks = dailyCounts.chunked(7)
    val activeDays = dailyCounts.count { it > 0 }

    Card(modifier = modifier.fillMaxWidth()) {
        Column(
            modifier = Modifier.padding(Spacing.m),
            verticalArrangement = Arrangement.spacedBy(Spacing.s),
        ) {
            Text(
                text = "$activeDays active days in the last ${weeks.size} weeks",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
            Row(horizontalArrangement = Arrangement.spacedBy(Spacing.xs)) {
                Column(verticalArrangement = Arrangement.spacedBy(Spacing.xs)) {
                    DayLabels.forEach { label ->
                        Box(
                            modifier = Modifier.height(CellSize),
                            contentAlignment = Alignment.CenterStart,
                        ) {
                            Text(
                                text = label,
                                style = MaterialTheme.typography.labelSmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                            )
                        }
                    }
                }
                weeks.forEach { week ->
                    Column(verticalArrangement = Arrangement.spacedBy(Spacing.xs)) {
                        week.forEach { count -> HeatCell(count = count) }
                    }
                }
            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(Spacing.xs),
            ) {
                Text(
                    text = "Less",
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
                (0..4).forEach { level -> HeatCell(count = level) }
                Text(
                    text = "More",
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            }
        }
    }
}

@Composable
private fun HeatCell(count: Int) {
    Box(
        modifier = Modifier
            .size(CellSize)
            .background(color = heatColor(count), shape = MaterialTheme.shapes.extraSmall)
    )
}

/** Same theme green, just more or less see-through. No new color values. */
@Composable
private fun heatColor(count: Int): Color {
    val colors = MaterialTheme.colorScheme
    return when {
        count <= 0 -> colors.outlineVariant
        count == 1 -> colors.secondary.copy(alpha = 0.35f)
        count == 2 -> colors.secondary.copy(alpha = 0.55f)
        count == 3 -> colors.secondary.copy(alpha = 0.8f)
        else -> colors.secondary
    }
}

@PreviewLightDark
@Composable
private fun ActivityHeatmapPreview() {
    MatomeTheme {
        Surface {
            ActivityHeatmap(
                dailyCounts = SampleData.activity,
                modifier = Modifier.padding(Spacing.m),
            )
        }
    }
}

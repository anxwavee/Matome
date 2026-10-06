package com.example.matome.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.example.matome.R
import com.example.matome.ui.theme.MatomeTheme
import com.example.matome.ui.theme.Spacing

private val IllustrationSize = 64.dp

@Composable
fun EmptyState(
    title: String,
    message: String,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(Spacing.l),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(Spacing.s),
    ) {
        Image(
            painter = painterResource(id = R.drawable.illustration_all_done),
            contentDescription = null, // decorative: the text below says everything
            modifier = Modifier.size(IllustrationSize),
            colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.secondary),
        )
        Text(
            text = title,
            style = MaterialTheme.typography.titleMedium,
            textAlign = TextAlign.Center,
        )
        Text(
            text = message,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center,
        )
    }
}

@PreviewLightDark
@Composable
private fun EmptyStatePreview() {
    MatomeTheme {
        Surface {
            EmptyState(
                title = "Nothing due today",
                message = "Enjoy the break, or get ahead on this week's tasks.",
            )
        }
    }
}

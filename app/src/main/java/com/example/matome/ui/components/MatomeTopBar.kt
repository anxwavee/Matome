package com.example.matome.ui.components

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.example.matome.ui.theme.MatomeTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MatomeTopBar(
    title: String,
    modifier: Modifier = Modifier,
    onBackClick: (() -> Unit)? = null,
    actions: @Composable RowScope.() -> Unit = {},
) {
    TopAppBar(
        title = {
            Text(text = title, maxLines = 1, overflow = TextOverflow.Ellipsis)
        },
        modifier = modifier,
        navigationIcon = {
            if (onBackClick != null) {
                IconButton(onClick = onBackClick) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back",
                    )
                }
            }
        },
        actions = actions,
    )
}

@PreviewLightDark
@Composable
private fun MatomeTopBarPreview() {
    MatomeTheme {
        MatomeTopBar(title = "Task details", onBackClick = {})
    }
}

@PreviewLightDark
@Composable
private fun MatomeTopBarWithActionPreview() {
    MatomeTheme {
        MatomeTopBar(
            title = "Welcome, Student",
            actions = {
                IconButton(onClick = {}) {
                    Icon(imageVector = Icons.Filled.Notifications, contentDescription = "Notifications")
                }
            },
        )
    }
}

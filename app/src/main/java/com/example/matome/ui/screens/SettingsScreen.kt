package com.example.matome.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.example.matome.data.SampleData
import com.example.matome.navigation.Routes
import com.example.matome.ui.components.MatomeBottomBar
import com.example.matome.ui.components.MatomeTopBar
import com.example.matome.ui.components.SectionHeader
import com.example.matome.ui.theme.MatomeTheme
import com.example.matome.ui.theme.Spacing

private val AvatarSize = 48.dp

@Composable
fun SettingsScreen(
    onBackClick: () -> Unit,
    onBottomNavClick: (route: String) -> Unit,
    modifier: Modifier = Modifier,
    userName: String = SampleData.USER_NAME,
) {

    var remindersOn by remember { mutableStateOf(true) }
    var showActivity by remember { mutableStateOf(true) }

    Scaffold(
        modifier = modifier,
        topBar = { MatomeTopBar(title = "Settings", onBackClick = onBackClick) },
        bottomBar = {
            MatomeBottomBar(currentRoute = Routes.SETTINGS, onItemClick = onBottomNavClick)
        },
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .padding(Spacing.m),
            verticalArrangement = Arrangement.spacedBy(Spacing.m),
        ) {
            SectionHeader(title = "Profile")
            Card(modifier = Modifier.fillMaxWidth()) {
                Row(
                    modifier = Modifier.padding(Spacing.m),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(Spacing.m),
                ) {
                    Icon(
                        imageVector = Icons.Filled.AccountCircle,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(AvatarSize),
                    )
                    Column {
                        Text(text = userName, style = MaterialTheme.typography.titleMedium)
                        Text(
                            text = "University student",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                        )
                    }
                }
            }

            SectionHeader(title = "Preferences")
            SettingSwitchRow(
                title = "Deadline reminders",
                subtitle = "Get notified before a task is due",
                checked = remindersOn,
                onCheckedChange = { remindersOn = it },
            )
            SettingSwitchRow(
                title = "Show activity on Home",
                subtitle = "The green activity grid",
                checked = showActivity,
                onCheckedChange = { showActivity = it },
            )
            Text(
                text = "Dark mode follows your phone's system setting.",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )

            SectionHeader(title = "About")
            Text(
                text = "Matome 1.0 — courses, tasks and deadlines in one place.",
                style = MaterialTheme.typography.bodyMedium,
            )
        }
    }
}

@Composable
private fun SettingSwitchRow(
    title: String,
    subtitle: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(Spacing.m),
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(text = title, style = MaterialTheme.typography.bodyLarge)
            Text(
                text = subtitle,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
        }
        Switch(checked = checked, onCheckedChange = onCheckedChange)
    }
}

@PreviewLightDark
@Composable
private fun SettingsScreenPreview() {
    MatomeTheme {
        SettingsScreen(onBackClick = {}, onBottomNavClick = {})
    }
}

@PreviewLightDark
@Composable
private fun SettingSwitchRowPreview() {
    MatomeTheme {
        Surface {
            SettingSwitchRow(
                title = "Deadline reminders",
                subtitle = "Get notified before a task is due",
                checked = true,
                onCheckedChange = {},
                modifier = Modifier.padding(Spacing.m),
            )
        }
    }
}

package com.example.matome.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.example.matome.navigation.Routes
import com.example.matome.ui.theme.MatomeTheme

private data class BottomNavItem(
    val route: String,
    val label: String,
    val icon: ImageVector,
)

private val bottomNavItems = listOf(
    BottomNavItem(Routes.HOME, "Home", Icons.Filled.Home),
    BottomNavItem(Routes.TASKS, "Tasks", Icons.AutoMirrored.Filled.List),
    BottomNavItem(Routes.SETTINGS, "Settings", Icons.Filled.Settings),
)
@Composable
fun MatomeBottomBar(
    currentRoute: String,
    onItemClick: (route: String) -> Unit,
    modifier: Modifier = Modifier,
) {
    NavigationBar(modifier = modifier) {
        bottomNavItems.forEach { item ->
            NavigationBarItem(
                selected = item.route == currentRoute,
                onClick = { onItemClick(item.route) },
                icon = { Icon(imageVector = item.icon, contentDescription = null) },
                label = { Text(text = item.label) },
            )
        }
    }
}

@PreviewLightDark
@Composable
private fun MatomeBottomBarPreview() {
    MatomeTheme {
        MatomeBottomBar(currentRoute = Routes.HOME, onItemClick = {})
    }
}

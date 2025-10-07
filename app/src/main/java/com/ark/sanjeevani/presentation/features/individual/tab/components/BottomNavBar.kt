package com.ark.sanjeevani.presentation.features.individual.tab.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.outlined.History
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import com.ark.sanjeevani.presentation.features.individual.tab.logic.TabDestinations

data class NavItem(
    val destination: TabDestinations,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val title: String
)

val navItems = listOf(
    NavItem(
        destination = TabDestinations.Home,
        selectedIcon = Icons.Filled.Home,
        unselectedIcon = Icons.Outlined.Home,
        title = "Home"
    ),
    NavItem(
        destination = TabDestinations.History,
        selectedIcon = Icons.Filled.History,
        unselectedIcon = Icons.Outlined.History,
        title = "History"
    )
)

@Composable
fun BottomNavBar(
    selectedDestination: TabDestinations,
    onTabSelected: (TabDestinations) -> Unit
) {
    NavigationBar {
        navItems.forEach { navItem ->
            val isSelected = selectedDestination == navItem.destination

            NavigationBarItem(
                selected = isSelected,
                onClick = {
                    if (!isSelected) {
                        onTabSelected(navItem.destination)
                    }
                },
                icon = {
                    Icon(
                        imageVector = if (isSelected) {
                            navItem.selectedIcon
                        } else {
                            navItem.unselectedIcon
                        },
                        tint = if (isSelected) {
                            MaterialTheme.colorScheme.primary
                        } else {
                            Color.Unspecified
                        },
                        contentDescription = navItem.title
                    )
                },
                label = {
                    Text(
                        text = navItem.title,
                        style = MaterialTheme.typography.labelMedium
                    )
                },
                alwaysShowLabel = true,
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = MaterialTheme.colorScheme.onSecondaryContainer,
                    selectedTextColor = MaterialTheme.colorScheme.onSurface,
                    unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                    unselectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant,
                    indicatorColor = MaterialTheme.colorScheme.secondaryContainer
                )
            )
        }
    }
}
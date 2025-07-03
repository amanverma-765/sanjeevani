package com.ark.sanjeevani.presentation.components

import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBarWithCenterText(
    modifier: Modifier = Modifier,
    centerText: String,
    navIcon: ImageVector? = null,
    onNavIconClicked: () -> Unit = {}
) {
    CenterAlignedTopAppBar(
        modifier = modifier,
        title = { Text(text = centerText) },
        navigationIcon = {
            if (navIcon != null) {
                IconButton(onClick = onNavIconClicked) {
                    Icon(
                        imageVector = navIcon,
                        contentDescription = "Navigation icon"
                    )
                }
            }
        }
    )
}
package com.ark.sanjeevani.presentation.features.home.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Notifications
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil3.ImageLoader
import coil3.compose.rememberAsyncImagePainter


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeTopBar(
    modifier: Modifier = Modifier,
    userName: String,
    userProfileUrl: String
) {
    TopAppBar(
        modifier = modifier,
        title = {
            Column {
                Text(
                    text = "Hello, $userName 👋",
                    fontWeight = FontWeight.SemiBold
                )
                Text(
                    text = "How do you feel today?",
                    style = MaterialTheme.typography.labelLarge,
                    fontWeight = FontWeight.Light
                )
            }
        },
        navigationIcon = {
            IconButton(
                onClick = {},
                colors = IconButtonDefaults.iconButtonColors(
                    containerColor = MaterialTheme.colorScheme.surfaceColorAtElevation(12.dp)
                ),
                modifier = Modifier.padding(end = 2.dp, start = 2.dp)
            ) {
                val profileImage = rememberAsyncImagePainter(
                    model = userProfileUrl,
                    imageLoader = ImageLoader(LocalContext.current)
                )
                Image(
                    painter = profileImage,
                    contentDescription = "Profile image",
                    modifier = Modifier.size(48.dp)
                )
            }
        },
        actions = {
            FilledIconButton(
                onClick = {},
                shape = RoundedCornerShape(40),
                colors = IconButtonDefaults.iconButtonColors(
                    containerColor = MaterialTheme.colorScheme.surfaceColorAtElevation(12.dp)
                )
            ) {
                Icon(
                    imageVector = Icons.Rounded.Notifications,
                    contentDescription = "Notification",
                )
            }
        }
    )
}
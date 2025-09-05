package com.ark.sanjeevani.presentation.features.home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Notifications
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBarScrollBehavior
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.ark.sanjeevani.presentation.components.FullScreenSearchBar
import com.ark.sanjeevani.utils.DefaultImageLoader
import com.ark.sanjeevani.utils.DefaultImageLoader.imageLoader


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeTopBar(
    modifier: Modifier = Modifier,
    isLoading: Boolean,
    scrollBehavior: SearchBarScrollBehavior,
    userName: String?,
    userProfileUrl: String?,
    onNotificationClicked: () -> Unit,
    onProfileClicked: () -> Unit
) {
    Column(
        modifier = modifier.background(
            TopAppBarDefaults.topAppBarColors().containerColor
        )
    ) {
        TopAppBar(
            title = {
                Column {
                    Text(
                        text = "Hello, ${userName ?: "User"} 👋",
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
                    onClick = onProfileClicked,
                    colors = IconButtonDefaults.iconButtonColors(
                        containerColor = MaterialTheme.colorScheme.surfaceColorAtElevation(12.dp)
                    ),
                    modifier = Modifier.padding(end = 2.dp, start = 2.dp)
                ) {
                    if (isLoading) CircularProgressIndicator(Modifier.padding(4.dp))
                    else AsyncImage(
                        model = userProfileUrl,
                        imageLoader = imageLoader,
                        contentDescription = "Profile Image",
                        contentScale = ContentScale.Crop,
                        placeholder = painterResource(id = com.ark.sanjeevani.R.drawable.default_profile),
                        error = painterResource(id = com.ark.sanjeevani.R.drawable.default_profile),
                        fallback = painterResource(id = com.ark.sanjeevani.R.drawable.default_profile),
                        modifier = Modifier.fillMaxSize()
                    )
                }
            },
            actions = {
                FilledIconButton(
                    onClick = onNotificationClicked,
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
        // Searchbar here
        FullScreenSearchBar(scrollBehavior = scrollBehavior)
    }
}
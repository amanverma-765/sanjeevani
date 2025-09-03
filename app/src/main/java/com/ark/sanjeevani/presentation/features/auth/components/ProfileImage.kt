package com.ark.sanjeevani.presentation.features.auth.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.ark.sanjeevani.R

@Composable
fun ProfileImage(
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    // Profile Image Section
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.fillMaxWidth()
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .size(150.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.surfaceVariant)
                .border(
                    2.dp,
                    MaterialTheme.colorScheme.outline.copy(.5f),
                    CircleShape
                )
                .clickable { onClick() }
        ) {
            Image(
                painter = painterResource(R.drawable.default_profile),
                contentDescription = "Profile Image",
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize().alpha(.5f)
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Add Photo",
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Medium
        )
    }
}
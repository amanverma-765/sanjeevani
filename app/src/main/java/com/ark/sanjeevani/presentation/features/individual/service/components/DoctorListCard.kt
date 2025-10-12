package com.ark.sanjeevani.presentation.features.individual.service.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bolt
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.ark.sanjeevani.domain.model.Doctor
import com.ark.sanjeevani.utils.DefaultImageLoader.imageLoader


@Composable
fun DoctorListCard(
    modifier: Modifier = Modifier,
    doctor: Doctor,
    specialization: String,
    onClick: () -> Unit
) {
    ListItem(
        modifier = modifier.clickable { onClick() },
        headlineContent = {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp)
            ) {
                Text(
                    text = doctor.name,
                    fontWeight = FontWeight.SemiBold,
                    maxLines = 2,
                    modifier = Modifier.weight(1f)
                )
                Text(
                    text = if (doctor.experienceYears / 2 >= 1) "${doctor.experienceYears} Yrs"
                    else "${doctor.experienceMonths} Months",
                    maxLines = 1,
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier
                        .background(
                            color = MaterialTheme.colorScheme.surfaceContainerHigh,
                            shape = CircleShape
                        )
                        .padding(horizontal = 8.dp)
                )
            }
        },
        leadingContent = {
            AsyncImage(
                model = doctor.avatar,
                imageLoader = imageLoader,
                contentDescription = "Profile Image",
                contentScale = ContentScale.Crop,
                placeholder = painterResource(id = com.ark.sanjeevani.R.drawable.default_profile),
                error = painterResource(id = com.ark.sanjeevani.R.drawable.default_profile),
                fallback = painterResource(id = com.ark.sanjeevani.R.drawable.default_profile),
                modifier = Modifier
                    .size(70.dp)
                    .aspectRatio(1f)
                    .clip(RoundedCornerShape(12.dp))
            )
        },
        supportingContent = {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(
                    imageVector = Icons.Default.Bolt,
                    contentDescription = null,
                    modifier = Modifier.size(16.dp)
                )
                Text(text = specialization, maxLines = 1)
            }
        }
    )
}
package com.ark.sanjeevani.presentation.features.hospital.components

import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.ark.sanjeevani.domain.model.Hospital
import com.ark.sanjeevani.utils.DefaultImageLoader.imageLoader


@Composable
fun HospitalCard(
    modifier: Modifier = Modifier,
    hospital: Hospital,
    onClick: () -> Unit
) {
    ElevatedCard(
        onClick = onClick,
        shape = RoundedCornerShape(20.dp),
        modifier = modifier
    ) {
        ListItem(
            colors = ListItemDefaults.colors(
                containerColor = Color.Transparent
            ),
            headlineContent = {
                Text(text = hospital.name, maxLines = 2)
            },
            leadingContent = {
                AsyncImage(
                    model = hospital.img,
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
                Text(text = "5km away", maxLines = 1)
            }
        )
    }
}
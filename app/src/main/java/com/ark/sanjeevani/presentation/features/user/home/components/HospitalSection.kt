package com.ark.sanjeevani.presentation.features.user.home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.ark.sanjeevani.R
import com.ark.sanjeevani.domain.enums.HospitalType


@Composable
fun HospitalSection(
    modifier: Modifier = Modifier,
    onClick: (hospitalType: HospitalType) -> Unit
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        modifier = modifier
    ) {
        HospitalCard(
            icon = R.drawable.gov_hospital_icon,
            title = "Gov Hospitals",
            onClick = { onClick(HospitalType.GOV) },
            modifier = Modifier.weight(1f)
        )
        HospitalCard(
            icon = R.drawable.private_hospital_icon,
            title = "Private Hospitals",
            onClick = { onClick(HospitalType.PRIVATE) },
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
private fun HospitalCard(
    modifier: Modifier = Modifier,
    title: String,
    icon: Int,
    onClick: () -> Unit
) {
    OutlinedCard(
        onClick = onClick,
        shape = RoundedCornerShape(20),
        modifier = modifier
            .fillMaxWidth()
            .height(80.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp)
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .fillMaxHeight()
                    .aspectRatio(1f)
                    .clip(RoundedCornerShape(20))
                    .background(MaterialTheme.colorScheme.surfaceTint.copy(.2f))
            ) {
                Icon(
                    painter = painterResource(icon),
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.padding(12.dp)
                )
            }

            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
        }
    }
}
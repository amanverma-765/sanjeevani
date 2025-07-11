package com.ark.sanjeevani.presentation.features.home.components

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialExpressiveTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.ark.sanjeevani.domain.model.Service
import com.valentinilk.shimmer.ShimmerTheme
import com.valentinilk.shimmer.rememberShimmer
import com.valentinilk.shimmer.shimmer


@Composable
fun ServiceSection(
    modifier: Modifier = Modifier,
    services: List<Service>,
    isLoading: Boolean,
    onClick: (serviceId: Int) -> Unit
) {
    FlowRow(
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        maxItemsInEachRow = 3,
        modifier = modifier
            .animateContentSize()
            .then(
            if (isLoading) {
                Modifier
                    .shimmer()
                    .clip(RoundedCornerShape(10))
                    .background(CardDefaults.cardColors().containerColor)
                    .fillMaxWidth()
                    .height(300.dp)
            } else Modifier
        )
    ) {
        services.forEach { service ->
            ServiceCard(
                service = service,
                onCLick = { onClick(service.id) },
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Composable
private fun ServiceCard(
    modifier: Modifier = Modifier,
    service: Service,
    onCLick: () -> Unit
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        Card(
            onClick = onCLick,
            border = BorderStroke(
                width = 1.dp,
                color = service.color
            ),
            colors = CardDefaults.cardColors(containerColor = service.color),
            shape = RoundedCornerShape(20),
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f)
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.fillMaxSize()
            ) {
                Icon(
                    painter = painterResource(service.icon),
                    contentDescription = service.description,
                    tint = Color.Black,
                    modifier = Modifier.size(60.dp)
                )
            }
        }
        Text(
            text = service.title,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.SemiBold,
            style = MaterialTheme.typography.titleSmall,
            overflow = TextOverflow.Clip,
            maxLines = 1
        )
    }
}
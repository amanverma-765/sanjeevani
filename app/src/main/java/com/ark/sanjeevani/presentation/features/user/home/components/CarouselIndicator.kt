package com.ark.sanjeevani.presentation.features.user.home.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun CarouselIndicator(
    currentPage: Int,
    totalDots: Int,
    modifier: Modifier = Modifier
) {
    val activeDot = MaterialTheme.colorScheme.primary
    val inactiveDot = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.2f)

    Row(
        modifier = modifier.fillMaxWidth(), // Fill available width
        horizontalArrangement = Arrangement.spacedBy(
            space = 6.dp,
            alignment = Alignment.CenterHorizontally // Center the content
        ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        repeat(totalDots) { index ->
            val isActive = index == currentPage % totalDots

            val width by animateDpAsState(
                targetValue = if (isActive) 24.dp else 6.dp,
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioMediumBouncy,
                    stiffness = Spring.StiffnessMedium
                ),
                label = "pill_width_$index"
            )

            val color by animateColorAsState(
                targetValue = if (isActive) activeDot else inactiveDot,
                animationSpec = tween(300),
                label = "pill_color_$index"
            )

            Box(
                modifier = Modifier
                    .width(width)
                    .height(6.dp)
                    .background(
                        color = color,
                        shape = RoundedCornerShape(3.dp)
                    )
            )
        }
    }
}
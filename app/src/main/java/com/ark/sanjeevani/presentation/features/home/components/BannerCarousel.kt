package com.ark.sanjeevani.presentation.features.home.components

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.Box
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.BrokenImage
import androidx.compose.material.icons.rounded.ImageSearch
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import coil3.compose.AsyncImagePainter
import com.ark.sanjeevani.domain.model.BannerItem
import com.ark.sanjeevani.utils.DefaultImageLoader.imageLoader
import com.valentinilk.shimmer.shimmer
import kotlinx.coroutines.delay


@Composable
fun BannerCarousel(
    modifier: Modifier = Modifier,
    banners: List<BannerItem>,
    isLoading: Boolean,
    onClick: () -> Unit
) {
    val pagerState = rememberPagerState(
        initialPage = banners.size * 1000,
        pageCount = { Int.MAX_VALUE }
    )

    LaunchedEffect(Unit) {
        while (true) {
            delay(3000)
            pagerState.animateScrollToPage(
                page = pagerState.currentPage + 1,
                animationSpec = tween(500)
            )
        }
    }

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.animateContentSize()
    ) {
        if (banners.isEmpty()) BannerPlaceholder(isLoading = isLoading)
        else {
            HorizontalPager(
                pageSpacing = 16.dp,
                beyondViewportPageCount = 1,
                state = pagerState,
                modifier = Modifier.fillMaxWidth()
            ) { page ->
                val actualPage = page % banners.size
                val bannerItem = banners[actualPage]
                var isImgLoading by remember { mutableStateOf(false) }
                var isError by remember { mutableStateOf(false) }

                Card(
                    onClick = onClick,
                    shape = RoundedCornerShape(15),
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(2f / 1f)
                ) {
                    Box(modifier = Modifier.fillMaxSize()) {
                        if (isError || isImgLoading) BannerPlaceholder(
                            isLoading = isImgLoading,
                            isError = isError
                        )
                        AsyncImage(
                            model = bannerItem.imageUrl,
                            imageLoader = imageLoader,
                            contentDescription = bannerItem.description,
                            alignment = Alignment.Center,
                            contentScale = ContentScale.Crop,
                            onState = { state ->
                                when (state) {
                                    is AsyncImagePainter.State.Loading -> {
                                        isError = false
                                        isImgLoading = true
                                    }

                                    is AsyncImagePainter.State.Error -> {
                                        isImgLoading = false
                                        isError = true
                                    }

                                    else -> {
                                        isImgLoading = false
                                        isError = false
                                    }
                                }
                            },
                            modifier = Modifier.fillMaxSize()
                        )
                    }
                }
            }
            DotIndicator(
                currentPage = pagerState.currentPage,
                totalDots = banners.size,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp)
            )
        }
    }
}

@Composable
private fun DotIndicator(
    currentPage: Int,
    totalDots: Int,
    modifier: Modifier = Modifier
) {
    val activeDot = MaterialTheme.colorScheme.surfaceTint.copy(.7f)
    val inactiveDot = MaterialTheme.colorScheme.surfaceColorAtElevation(24.dp)

    Canvas(modifier = modifier.height(16.dp)) {
        val indicatorSize = 8.dp.toPx()
        val spacing = 4.dp.toPx()
        val totalWidth = totalDots * indicatorSize + (totalDots - 1) * spacing
        val startX = (size.width - totalWidth) / 2

        for (i in 0 until totalDots) {
            drawCircle(
                color = if (i == currentPage % totalDots) activeDot else inactiveDot,
                radius = indicatorSize / 2,
                center = Offset(
                    startX + i * (indicatorSize + spacing) + indicatorSize / 2,
                    size.height / 2
                )
            )
        }
    }
}

@Composable
private fun BannerPlaceholder(
    modifier: Modifier = Modifier,
    isLoading: Boolean,
    isError: Boolean = false
) {
    Box(
        modifier = modifier
            .then(if (isLoading) Modifier.shimmer() else Modifier)
            .fillMaxWidth()
            .aspectRatio(2f / 1f)
            .clip(RoundedCornerShape(15))
            .background(CardDefaults.cardColors().containerColor),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = if (isError) Icons.Rounded.BrokenImage else Icons.Rounded.ImageSearch,
            contentDescription = "Error loading image",
            modifier = Modifier
                .size(50.dp)
                .align(Alignment.Center)
        )
    }
}
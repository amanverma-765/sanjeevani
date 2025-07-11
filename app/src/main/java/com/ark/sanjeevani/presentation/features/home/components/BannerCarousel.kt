package com.ark.sanjeevani.presentation.features.home.components

import androidx.compose.foundation.layout.Box
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
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
import androidx.compose.material.icons.filled.Image
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
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
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import coil3.compose.AsyncImagePainter
import com.ark.sanjeevani.domain.model.BannerItem
import com.ark.sanjeevani.utils.ImageLoaderProvider.imageLoader
import kotlinx.coroutines.delay


@Composable
fun BannerCarousel(
    modifier: Modifier = Modifier,
    banners: List<BannerItem>,
    onClick: () -> Unit
) {
    if (banners.isEmpty()) return
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

    Column(modifier = modifier) {
        HorizontalPager(
            pageSpacing = 16.dp,
            beyondViewportPageCount = 1,
            state = pagerState,
            modifier = Modifier.fillMaxWidth()
        ) { page ->
            val actualPage = page % banners.size
            val bannerItem = banners[actualPage]
            var isLoading by remember { mutableStateOf(false) }
            var isError by remember { mutableStateOf(false) }

            Card(
                onClick = onClick,
                shape = RoundedCornerShape(15),
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(2f / 1f)
            ) {
                Box(modifier = Modifier.fillMaxSize()) {
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
                                    isLoading = true
                                }

                                is AsyncImagePainter.State.Error -> {
                                    isLoading = false
                                    isError = true
                                }

                                else -> {
                                    isLoading = false
                                    isError = false
                                }
                            }
                        },
                        modifier = Modifier.fillMaxSize()
                    )

                    if (isLoading) {
                        CircularProgressIndicator(
                            modifier = Modifier.align(Alignment.Center)
                        )
                    }

                    if (isError) {
                        Image(
                            imageVector = Icons.Default.Image,
                            contentDescription = "Error loading image",
                            modifier = Modifier
                                .size(50.dp)
                                .align(Alignment.Center)
                        )
                    }
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
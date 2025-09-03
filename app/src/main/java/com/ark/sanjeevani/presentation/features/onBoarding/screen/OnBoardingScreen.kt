package com.ark.sanjeevani.presentation.features.onBoarding.screen

import PagerIndicator
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.ark.sanjeevani.presentation.features.onBoarding.components.OnboardingButton
import com.ark.sanjeevani.presentation.features.onBoarding.logic.onBoardingPages
import kotlinx.coroutines.launch

@Composable
fun OnboardingScreen(
    modifier: Modifier = Modifier,
    onFinishClick: () -> Unit
) {

    val pagerState = rememberPagerState(initialPage = 0) { onBoardingPages.size }
    val scope = rememberCoroutineScope()

    HorizontalPager(
        state = pagerState,
        modifier = Modifier.fillMaxWidth()
    ) { page ->
        val item = onBoardingPages[page]
        Column(modifier.fillMaxSize()) {
            Image(
                painter = painterResource(item.imageRes),
                contentDescription = "Onboarding Image",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(.65f)
            )
            Column(
                verticalArrangement = Arrangement.SpaceAround,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp)
            ) {
                PagerIndicator(
                    pageCount = onBoardingPages.size,
                    currentPage = page
                )

                Text(
                    text = item.title,
                    style = MaterialTheme.typography.titleLarge,
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = item.description,
                    style = MaterialTheme.typography.bodyLarge,
                    textAlign = TextAlign.Center
                )

                OnboardingButton(
                    text = "Next",
                    modifier = Modifier.fillMaxWidth(),
                    onClick = {
                        if (page == onBoardingPages.size - 1) {
                            onFinishClick()
                        } else {
                            scope.launch {
                                pagerState.animateScrollToPage(
                                    page = page + 1,
                                    animationSpec = tween(500)
                                )
                            }
                        }
                    }
                )

                TextButton(onClick = onFinishClick) {
                    Text(
                        text = "Skip",
                        style = MaterialTheme.typography.titleMedium,
                    )
                }
            }
        }
    }
}
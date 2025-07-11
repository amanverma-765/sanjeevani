package com.ark.sanjeevani.presentation.features.home.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ark.sanjeevani.domain.model.mockBanners
import com.ark.sanjeevani.domain.model.mockServices
import com.ark.sanjeevani.presentation.features.home.components.BannerCarousel
import com.ark.sanjeevani.presentation.features.home.components.HomeTopBar
import com.ark.sanjeevani.presentation.features.home.components.HospitalSection
import com.ark.sanjeevani.presentation.features.home.components.ServicesSection
import com.ark.sanjeevani.presentation.features.home.logic.HomeUiEvent
import com.ark.sanjeevani.presentation.features.home.logic.HomeViewModel
import com.ark.sanjeevani.utils.plus
import com.ark.sanjeevani.utils.toastShort
import org.koin.androidx.compose.koinViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = koinViewModel(),
    onNotificationClicked: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current

    uiState.errorMsg?.let { errorMsg ->
        LaunchedEffect(errorMsg) {
            context.toastShort(errorMsg)
            viewModel.onEvent(HomeUiEvent.ClearErrorMsg)
        }
    }

    Scaffold(
        topBar = {
            HomeTopBar(
                userName = uiState.userInfo?.name?.split(" ")?.firstOrNull() ?: "User",
                userProfileUrl = uiState.userInfo?.profileUrl ?: "",
                onNotificationClicked = onNotificationClicked,
                onProfileClicked = {}
            )
        }
    ) { innerPadding ->
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            contentPadding = innerPadding.plus(PaddingValues(horizontal = 16.dp, vertical = 8.dp)),
            modifier = modifier.fillMaxSize()
        ) {
            item { BannerCarousel(banners = mockBanners) }
            item { HospitalSection() }
            item { ServicesSection(services = mockServices) }
            // Not Decided what to show in this section
            items(5) {
                Card(
                    shape = RoundedCornerShape(20),
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(2f / 0.7f)
                ) {

                }
            }
        }
    }
}
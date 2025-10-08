package com.ark.sanjeevani.presentation.features.individual.home.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ark.sanjeevani.domain.enums.HospitalType
import com.ark.sanjeevani.presentation.features.individual.home.components.BannerCarousel
import com.ark.sanjeevani.presentation.features.individual.home.components.HospitalSection
import com.ark.sanjeevani.presentation.features.individual.home.components.ServiceSection
import com.ark.sanjeevani.presentation.features.individual.home.logic.HomeUiEvent
import com.ark.sanjeevani.presentation.features.individual.home.logic.HomeViewModel
import com.ark.sanjeevani.presentation.features.individual.home.logic.ServiceType
import com.ark.sanjeevani.presentation.features.individual.home.logic.serviceItems
import com.ark.sanjeevani.utils.toastShort
import org.koin.androidx.compose.koinViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = koinViewModel(),
    onHospitalClick: (hospitalType: HospitalType) -> Unit,
    onServiceClick: (type: ServiceType) -> Unit,
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current

    LaunchedEffect(uiState.errorMsg) {
        uiState.errorMsg?.let { errorMsg ->
            context.toastShort(errorMsg)
            viewModel.onEvent(HomeUiEvent.ClearError)
        }
    }

    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        contentPadding = PaddingValues(horizontal = 12.dp, vertical = 8.dp),
        modifier = modifier.fillMaxSize()
    ) {
        if (uiState.bannerError == null) {
            item {
                BannerCarousel(
                    banners = uiState.banners,
                    isLoading = uiState.isBannerLoading,
                    onClick = {},
                )
            }
        }

        item { HospitalSection(onClick = onHospitalClick) }
        item {
            ServiceSection(
                serviceItems = serviceItems,
                onClick = {
                    onServiceClick(it)
                }
            )
        }
    }
}
package com.ark.sanjeevani.presentation.features.individual.tab.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.ark.sanjeevani.presentation.features.individual.history.screen.HistoryScreen
import com.ark.sanjeevani.presentation.features.individual.home.components.HomeTopBar
import com.ark.sanjeevani.presentation.features.individual.home.screen.HomeScreen
import com.ark.sanjeevani.presentation.features.individual.tab.components.BottomNavBar
import com.ark.sanjeevani.presentation.features.individual.tab.logic.TabDestinations
import com.ark.sanjeevani.presentation.features.individual.tab.logic.TabUiEvent
import com.ark.sanjeevani.presentation.features.individual.tab.logic.TabViewModel
import com.ark.sanjeevani.presentation.navigation.RootDestinations
import com.ark.sanjeevani.utils.NetworkViewModel
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun TabContainer(
    modifier: Modifier = Modifier,
    initialTab: TabDestinations?,
    navController: NavController,
    networkVM: NetworkViewModel = viewModel(),
    viewModel: TabViewModel = koinViewModel { parametersOf(initialTab) }
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val networkState by networkVM.networkState.collectAsState()

    LaunchedEffect(uiState.registrationError, uiState.authError) {
        uiState.authError?.let {
            if (networkState.isConnected) {
                navController.navigate(RootDestinations.Localization) {
                    popUpTo(0) { inclusive = true }
                    launchSingleTop = true
                }
            }
        }
    }

    LaunchedEffect(uiState.userInfo) {
        uiState.userInfo?.let {
            viewModel.onEvent(TabUiEvent.GetRegisteredUser(it.email))
        }
    }

    LaunchedEffect(uiState.registeredUser, uiState.registrationError) {
        if (uiState.registeredUser == null && uiState.registrationError != null) {
            if (networkState.isConnected) {
                navController.navigate(RootDestinations.Registration) {
                    popUpTo(0) { inclusive = true }
                    launchSingleTop = true
                }
            }
        }
    }

    Scaffold(
        topBar = {
            HomeTopBar(
                userName = uiState.registeredUser?.getFirstName(),
                userProfileUrl = uiState.registeredUser?.avatar,
                onNotificationClicked = {
                    navController.navigate(RootDestinations.Notification)
                },
                onProfileClicked = {
                    navController.navigate(RootDestinations.Profile)
                },
                isLoading = uiState.isUserLoading
            )
        },
        bottomBar = {
            BottomNavBar(
                selectedDestination = uiState.selectedDestination,
                onTabSelected = { destination ->
                    viewModel.onEvent(TabUiEvent.SelectDestination(destination))
                }
            )
        }
    ) { innerPadding ->

        Box(
            modifier = modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            when (uiState.selectedDestination) {
                TabDestinations.Home -> {
                    HomeScreen(
                        onHospitalClicked = { type ->
                            navController.navigate(RootDestinations.Hospital(type))
                        }
                    )
                }

                TabDestinations.History -> {
                    HistoryScreen()
                }
            }
        }
    }
}
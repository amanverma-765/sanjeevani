package com.ark.sanjeevani.presentation.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.ark.sanjeevani.presentation.features.auth.screen.LoginScreen
import com.ark.sanjeevani.presentation.features.auth.screen.RegistrationScreen
import com.ark.sanjeevani.presentation.features.individual.hospital.screen.HospitalDetailScreen
import com.ark.sanjeevani.presentation.features.individual.hospital.screen.HospitalListScreen
import com.ark.sanjeevani.presentation.features.individual.notification.screen.NotificationScreen
import com.ark.sanjeevani.presentation.features.individual.profile.screen.ProfileScreen
import com.ark.sanjeevani.presentation.features.individual.tab.logic.TabDestinations
import com.ark.sanjeevani.presentation.features.individual.tab.logic.createTabDestination
import com.ark.sanjeevani.presentation.features.individual.tab.logic.getTabDestination
import com.ark.sanjeevani.presentation.features.individual.tab.screen.TabContainer
import com.ark.sanjeevani.presentation.features.onBoarding.screen.LocalizationScreen
import com.ark.sanjeevani.presentation.features.onBoarding.screen.OnboardingScreen
import com.ark.sanjeevani.utils.AnimatedNavHost
import com.ark.sanjeevani.utils.safePopBackStack


@Composable
fun RootNavHost(
    modifier: Modifier = Modifier,
    startDestination: RootDestinations
) {
    val navController = rememberNavController()

    AnimatedNavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier.fillMaxSize()
    ) {
        composable<RootDestinations.Tab> { navBackStack ->
            val tab = navBackStack.toRoute<RootDestinations.Tab>()
            TabContainer(
                navController = navController,
                initialTab = getTabDestination(tab.initialTab)
            )
        }

        composable<RootDestinations.Localization> {
            LocalizationScreen(
                onLanguageSelected = {
                    navController.navigate(RootDestinations.Onboarding)
                }
            )
        }

        composable<RootDestinations.Onboarding> {
            OnboardingScreen(
                onFinishClick = {
                    navController.navigate(RootDestinations.Login) {
                        popUpTo(0) { inclusive = true } // clear everything
                        launchSingleTop = true
                    }
                }
            )
        }

        composable<RootDestinations.Login> {
            LoginScreen(
                onLoginSuccessfully = {
                    navController.navigate(
                        RootDestinations.Tab(
                            createTabDestination(TabDestinations.Home)
                        )
                    ) {
                        popUpTo(0) { inclusive = true }
                        launchSingleTop = true
                    }
                }
            )
        }

        composable<RootDestinations.Registration> {
            RegistrationScreen(
                onRegCompleted = {
                    navController.navigate(RootDestinations.Tab) {
                        popUpTo(0) { inclusive = true }
                        launchSingleTop = true
                    }
                },
                onUserNotAuthenticated = {
                    navController.navigate(RootDestinations.Login) {
                        popUpTo(0) { inclusive = true }
                        launchSingleTop = true
                    }
                }
            )
        }

        composable<RootDestinations.Hospital> { navBackStack ->
            val hospital = navBackStack.toRoute<RootDestinations.Hospital>()
            HospitalListScreen(
                type = hospital.type,
                onHospitalClicked = {
                    navController.navigate(RootDestinations.HospitalDetail(it))
                },
                onBackClicked = {
                    navController.safePopBackStack()
                }
            )
        }

        composable<RootDestinations.HospitalDetail> { navBackStack ->
            val hospitalDetail = navBackStack.toRoute<RootDestinations.HospitalDetail>()
            HospitalDetailScreen(
                hospitalId = hospitalDetail.id,
                onBackClicked = {
                    navController.safePopBackStack()
                }
            )
        }

        composable<RootDestinations.Notification> {
            NotificationScreen()
        }

        composable<RootDestinations.Profile> {
            ProfileScreen()
        }
    }
}

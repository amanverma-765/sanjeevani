package com.ark.sanjeevani.presentation.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ark.sanjeevani.presentation.features.auth.screen.LoginScreen
import com.ark.sanjeevani.presentation.features.auth.screen.RegistrationScreen
import com.ark.sanjeevani.presentation.features.home.screen.HomeScreen
import com.ark.sanjeevani.presentation.features.notification.screen.NotificationScreen
import com.ark.sanjeevani.presentation.features.onBoarding.screen.LocalizationScreen
import com.ark.sanjeevani.presentation.features.onBoarding.screen.OnboardingScreen
import com.ark.sanjeevani.utils.AnimatedNavHost
import com.ark.sanjeevani.utils.safePopBackStack


@Composable
fun RootNavHost(
    modifier: Modifier = Modifier,
    startDestination: Destinations
) {
    val navController = rememberNavController()

    AnimatedNavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier.fillMaxSize()
    ) {

        composable<Destinations.Localization> {
            LocalizationScreen(
                onLanguageSelected = {
                    navController.navigate(Destinations.Onboarding)
                }
            )
        }

        composable<Destinations.Onboarding> {
            OnboardingScreen(
                onFinishClick = {
                    navController.navigate(Destinations.Login) {
                        popUpTo(0) { inclusive = true } // clear everything
                        launchSingleTop = true
                    }
                }
            )
        }

        composable<Destinations.Login> {
            LoginScreen(
                onLoginSuccessfully = {
                    navController.navigate(Destinations.Home) {
                        popUpTo(0) { inclusive = true }
                        launchSingleTop = true
                    }
                }
            )
        }

        composable<Destinations.Registration> {
            RegistrationScreen(
                onRegCompleted = {
                    navController.navigate(Destinations.Home) {
                        popUpTo(0) { inclusive = true }
                        launchSingleTop = true
                    }
                },
                onUserNotAuthenticated = {
                    navController.navigate(Destinations.Login) {
                        popUpTo(0) { inclusive = true }
                        launchSingleTop = true
                    }
                }
            )
        }

        composable<Destinations.Home> {
            HomeScreen(
                onNotificationClicked = {
                    navController.navigate(Destinations.Notification)
                },
                onHospitalClicked = {},
                onNavigateToRegistration = {
                    navController.navigate(Destinations.Registration) {
                        popUpTo(0) { inclusive = true }
                        launchSingleTop = true
                    }
                },
                onNavigateToLoginScreen = {
                    navController.navigate(Destinations.Localization) {
                        popUpTo(0) { inclusive = true }
                        launchSingleTop = true
                    }
                }
            )
        }

        composable<Destinations.Notification> {
            NotificationScreen(
                onBackClicked = { navController.safePopBackStack() }
            )
        }
    }
}

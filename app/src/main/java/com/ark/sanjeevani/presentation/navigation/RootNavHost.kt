package com.ark.sanjeevani.presentation.navigation

import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ark.sanjeevani.presentation.features.auth.screen.LoginScreen
import com.ark.sanjeevani.presentation.features.auth.screen.RegistrationScreen
import com.ark.sanjeevani.presentation.features.home.screen.HomeScreen
import com.ark.sanjeevani.presentation.features.notification.screen.NotificationScreen
import com.ark.sanjeevani.presentation.features.onBoarding.screen.LocalizationScreen
import com.ark.sanjeevani.presentation.features.onBoarding.screen.OnboardingScreen


@Composable
fun RootNavHost(
    modifier: Modifier = Modifier,
    startDestination: Destinations
) {

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = startDestination,
        enterTransition = { slideInHorizontally(tween(500)) { it } },
        exitTransition = { slideOutHorizontally(tween(500)) { -it } },
        popEnterTransition = { slideInHorizontally(tween(500)) { -it } },
        popExitTransition = { slideOutHorizontally(tween(500)) { it } },
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
                    navController.navigate(Destinations.Login)
                }
            )
        }

        composable<Destinations.Login> {
            LoginScreen(
                onLoginWithGoogleClick = {
                    navController.navigate(Destinations.Registration)
                },
                onLoginWithFacebookClick = {
                    navController.navigate(Destinations.Registration)
                }
            )
        }

        composable<Destinations.Registration> {
            RegistrationScreen(
                onRegistrationSuccess = {

                }
            )
        }

        composable<Destinations.Home> {
            HomeScreen(
                onNotificationClicked = {
                    navController.navigate(Destinations.Notification)
                },
                onHospitalClicked = {}
            )
        }

        composable<Destinations.Notification> {
            NotificationScreen(
                onBackClicked = { navController.navigateUp() }
            )
        }

    }
}
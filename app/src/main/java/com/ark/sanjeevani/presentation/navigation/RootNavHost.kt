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
import com.ark.sanjeevani.presentation.features.home.screen.HomeScreen
import com.ark.sanjeevani.presentation.features.notification.screen.NotificationScreen
import com.ark.sanjeevani.presentation.features.onBoarding.screen.LocalizationScreen


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
                    navController.navigate(Destinations.Login)
                }
            )
        }

        composable<Destinations.Login> {
            LoginScreen(
                onSuccessFullAuth = {
                    navController.navigate(Destinations.Home) {
                        launchSingleTop = true
                        popUpTo(0) {
                            inclusive = true
                        }
                    }
                }
            )
        }

        composable<Destinations.Registration> {

        }

        composable<Destinations.Home> {
            HomeScreen(
                onUserNotAuthenticated = {
                    navController.navigate(Destinations.Localization) {
                        launchSingleTop = true
                        popUpTo(0) {
                            inclusive = true
                        }
                    }
                },
                onNotificationClicked = {
                    navController.navigate(Destinations.Notification)
                }
            )
        }

        composable<Destinations.Notification> {
            NotificationScreen(
                onBackClicked = { navController.navigateUp() }
            )
        }

    }
}
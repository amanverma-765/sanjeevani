package com.ark.sanjeevani.utils

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import kotlin.reflect.KClass

@Composable
fun AnimatedNavHost(
    navController: NavHostController,
    startDestination: Any,
    modifier: Modifier = Modifier,
    builder: NavGraphBuilder.() -> Unit
) {
    val animationDuration = 250

    NavHost(
        navController = navController,
        startDestination = startDestination,
        enterTransition = {
            slideInHorizontally(
                animationSpec = tween(animationDuration),
                initialOffsetX = { it }
            ) + fadeIn(
                animationSpec = tween(animationDuration)
            )
        },
        exitTransition = {
            fadeOut(
                animationSpec = tween(animationDuration)
            ) + scaleOut(
                animationSpec = tween(animationDuration),
                targetScale = 0.95f
            )
        },
        popEnterTransition = {
            fadeIn(
                animationSpec = tween(animationDuration)
            ) + scaleIn(
                animationSpec = tween(animationDuration),
                initialScale = 0.95f
            )
        },
        popExitTransition = {
            slideOutHorizontally(
                animationSpec = tween(animationDuration),
                targetOffsetX = { it }
            ) + fadeOut(
                animationSpec = tween(animationDuration)
            )
        },
        modifier = modifier.fillMaxSize(),
        builder = builder
    )
}


fun NavController.safePopBackStack(): Boolean {
    return if (previousBackStackEntry != null) {
        popBackStack()
    } else {
        false
    }
}


fun <T : Any> NavController.safePopBackStack(
    route: T,
    inclusive: Boolean = false,
    saveState: Boolean = false
): Boolean {
    return try {
        popBackStack(route, inclusive, saveState)
    } catch (e: Exception) {
        e.printStackTrace()
        false
    }
}


fun <T : Any> NavController.safePopBackStack(
    route: KClass<T>,
    inclusive: Boolean = false,
    saveState: Boolean = false
): Boolean {
    return try {
        popBackStack(route, inclusive, saveState)
    } catch (e: Exception) {
        e.printStackTrace()
        false
    }
}
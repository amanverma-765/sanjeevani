package com.ark.sanjeevani

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import co.touchlab.kermit.Logger
import com.ark.sanjeevani.presentation.features.auth.logic.AuthViewModel
import com.ark.sanjeevani.presentation.navigation.Destinations
import com.ark.sanjeevani.presentation.navigation.RootNavHost
import com.ark.sanjeevani.presentation.theme.SanjeevaniTheme
import org.koin.android.ext.android.inject

class MainActivity : ComponentActivity() {
    val authVm: AuthViewModel by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen().apply {
            setKeepOnScreenCondition { authVm.isLoading.value }
        }
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        setContent {
            SanjeevaniTheme {
                Surface {
                    val userInfo by authVm.userInfo.collectAsStateWithLifecycle()
                    val isLoading by authVm.isLoading.collectAsStateWithLifecycle()
                    RootNavHost(
                        startDestination = if (userInfo == null && !isLoading) {
                            Destinations.Localization
                        } else Destinations.Home
                    )
                }
            }
        }
    }
}


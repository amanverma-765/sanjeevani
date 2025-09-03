package com.ark.sanjeevani

import android.graphics.Color.TRANSPARENT
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.ark.sanjeevani.presentation.navigation.Destinations
import com.ark.sanjeevani.presentation.navigation.RootNavHost
import com.ark.sanjeevani.presentation.theme.SanjeevaniTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.light(TRANSPARENT, TRANSPARENT),
            navigationBarStyle = SystemBarStyle.light(TRANSPARENT, TRANSPARENT)
        )
        super.onCreate(savedInstanceState)
        setContent {
            SanjeevaniTheme {
                Surface(Modifier.fillMaxSize()) {
                    RootNavHost(
                        startDestination = Destinations.Registration
                    )
                }
            }
        }
    }
}


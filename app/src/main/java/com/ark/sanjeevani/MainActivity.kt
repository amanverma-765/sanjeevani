package com.ark.sanjeevani

import android.graphics.Color.TRANSPARENT
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.ark.sanjeevani.presentation.components.NetworkStatusDialog
import com.ark.sanjeevani.presentation.navigation.Destinations
import com.ark.sanjeevani.presentation.navigation.RootNavHost
import com.ark.sanjeevani.presentation.theme.SanjeevaniTheme
import dev.jordond.connectivity.Connectivity

class MainActivity : ComponentActivity() {
    private lateinit var connectivity: Connectivity
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.light(TRANSPARENT, TRANSPARENT),
            navigationBarStyle = SystemBarStyle.light(TRANSPARENT, TRANSPARENT)
        )
        super.onCreate(savedInstanceState)
        connectivity = Connectivity()
        setContent {
            LaunchedEffect(Unit) {
                connectivity.start()
            }
            SanjeevaniTheme {
                Surface(Modifier.fillMaxSize()) {
                    Box {
                        RootNavHost(
                            startDestination = Destinations.Home
                        )
                        NetworkStatusDialog(connectivity = connectivity)
                    }
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        // Clean up connectivity monitoring
        if (::connectivity.isInitialized) {
            connectivity.stop()
        }
    }
}


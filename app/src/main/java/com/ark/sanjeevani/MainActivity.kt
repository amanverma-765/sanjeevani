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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ark.sanjeevani.presentation.features.individual.tab.logic.TabDestinations
import com.ark.sanjeevani.presentation.features.individual.tab.logic.createTabDestination
import com.ark.sanjeevani.presentation.navigation.IndividualDestinations
import com.ark.sanjeevani.presentation.navigation.RootNavHost
import com.ark.sanjeevani.presentation.theme.SanjeevaniTheme
import com.ark.sanjeevani.utils.NetworkStatusDialog
import com.ark.sanjeevani.utils.NetworkViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.light(TRANSPARENT, TRANSPARENT),
            navigationBarStyle = SystemBarStyle.light(TRANSPARENT, TRANSPARENT)
        )
        super.onCreate(savedInstanceState)
        setContent {
            val networkViewModel: NetworkViewModel = viewModel()
            val networkState by networkViewModel.networkState.collectAsState()
            SanjeevaniTheme {
                Surface(Modifier.fillMaxSize()) {
                    Box {
                        key(networkState.reconnectionCount) {
                            RootNavHost(
                                startDestination = IndividualDestinations.Tab(
                                    createTabDestination(TabDestinations.Home)
                                )
                            )
                        }
                        NetworkStatusDialog(
                            networkState = networkState
                        )
                    }
                }
            }
        }
    }
}
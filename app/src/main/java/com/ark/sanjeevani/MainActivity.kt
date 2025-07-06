package com.ark.sanjeevani

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Surface
import com.ark.sanjeevani.presentation.navigation.Destinations
import com.ark.sanjeevani.presentation.navigation.RootNavHost
import com.ark.sanjeevani.presentation.theme.SanjeevaniTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.light(
                android.graphics.Color.TRANSPARENT,
                android.graphics.Color.TRANSPARENT
            ),
            navigationBarStyle = SystemBarStyle.light(
                android.graphics.Color.TRANSPARENT,
                android.graphics.Color.TRANSPARENT
            )
        )
        setContent {
            SanjeevaniTheme {
                Surface {
                    RootNavHost(startDestination = Destinations.Home)
                }
            }
        }
    }
}

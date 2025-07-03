package com.ark.sanjeevani

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.ark.sanjeevani.presentation.navigation.Destinations
import com.ark.sanjeevani.presentation.navigation.RootNavHost
import com.ark.sanjeevani.ui.theme.SanjeevaniTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SanjeevaniTheme {
                RootNavHost(startDestination = Destinations.Home)
            }
        }
    }
}

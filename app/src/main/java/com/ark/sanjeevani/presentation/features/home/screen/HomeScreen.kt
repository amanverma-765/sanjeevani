package com.ark.sanjeevani.presentation.features.home.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import co.touchlab.kermit.Logger
import coil3.ImageLoader
import coil3.compose.rememberAsyncImagePainter
import com.ark.sanjeevani.presentation.features.home.logic.HomeUiEvent
import com.ark.sanjeevani.presentation.features.home.logic.HomeViewModel
import org.koin.androidx.compose.koinViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = koinViewModel(),
    onUserNotAuthenticated: () -> Unit
) {

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(true) {
        viewModel.onEvent(HomeUiEvent.GetAuthenticatedUser)
    }

    LaunchedEffect(
        key1 = uiState.userInfo,
        key2 = uiState.errorMsg,
        key3 = uiState.isLoading
    ) {
        Logger.e("${uiState.userInfo}")
        if (!uiState.isLoading && uiState.userInfo == null && uiState.errorMsg != null) {
            onUserNotAuthenticated()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = """Welcome, ${
                            uiState.userInfo
                                ?.userMetadata?.getValue("name")
                                .toString().replace("\"", "")
                                .split(" ")
                                .firstOrNull()
                                ?: "User"
                        }""".trimMargin(),
                        color = MaterialTheme.colorScheme.primary
                    )
                },
                actions = {
                    IconButton(onClick = {}) {
                        val profileImage = rememberAsyncImagePainter(
                            model = uiState.userInfo?.userMetadata?.getValue("avatar_url")
                                .toString().replace("\"", ""),
                            imageLoader = ImageLoader(LocalContext.current)
                        )
                        Image(
                            painter = profileImage,
                            contentDescription = "Profile image",
                            modifier = Modifier.size(48.dp)
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        Box(
            modifier
                .fillMaxSize()
                .padding(innerPadding)
        )
    }
}
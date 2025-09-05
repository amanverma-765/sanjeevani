package com.ark.sanjeevani.presentation.features.auth.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ark.sanjeevani.R
import com.ark.sanjeevani.presentation.components.LoadingDialog
import com.ark.sanjeevani.presentation.features.auth.components.OAuthButton
import com.ark.sanjeevani.presentation.features.auth.logic.login.LoginUiEvent
import com.ark.sanjeevani.presentation.features.auth.logic.login.LoginViewModel
import com.ark.sanjeevani.utils.toastShort
import org.koin.androidx.compose.koinViewModel


@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    viewModel: LoginViewModel = koinViewModel(),
    onLoginSuccessfully: () -> Unit,
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current
    if (uiState.isLoading) LoadingDialog()

    LaunchedEffect(uiState.errorMsg) {
        uiState.errorMsg?.let {
            context.toastShort(it)
            viewModel.onEvent(LoginUiEvent.ClearErrorMsg)
        }
    }

    LaunchedEffect(uiState.userState) {
        if (uiState.userState != null) {
            onLoginSuccessfully()
        }
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = modifier.fillMaxSize()
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = modifier
                .fillMaxWidth()
                .fillMaxHeight(.65f)
        ) {
            Image(
                painter = painterResource(R.drawable.poster),
                contentDescription = "Doctors Poster",
                contentScale = ContentScale.FillWidth,
                alignment = Alignment.TopCenter,
            )
        }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp)
        ) {
            Text(
                text = "Welcome Back!",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )

            Text(
                text = "Choose your preferred sign-in method",
                style = MaterialTheme.typography.bodyLarge,
                textAlign = TextAlign.Center
            )

            // Google OAuth Button
            OAuthButton(
                onClick = {
                    context.toastShort("Logging in with Google")
                    viewModel.onEvent(LoginUiEvent.LoginWithGoogle(context))
                },
                icon = R.drawable.ic_google,
                text = "Continue with Google"
            )

            // Facebook OAuth Button
            OAuthButton(
                onClick = {
                    context.toastShort("Logging in with Facebook")
                    // loginViewModel.onEvent(LoginUiEvent.LoginWithFb(context))
                },
                icon = R.drawable.ic_facebook,
                text = "Continue with Facebook"
            )

            // Terms and Privacy
            Text(
                text = "By continuing, you agree to our Terms of Service\nand Privacy Policy",
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Center,
                lineHeight = 18.sp
            )
        }
    }
}
package com.ark.sanjeevani.presentation.features.auth.screen

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.ark.sanjeevani.R
import com.ark.sanjeevani.presentation.features.auth.components.LoginRoleSelector
import com.ark.sanjeevani.presentation.features.auth.components.LoginSection
import com.ark.sanjeevani.domain.enums.LoginRole
import com.ark.sanjeevani.ui.theme.loginScreenGradient
import com.ark.sanjeevani.utils.toastLong
import com.ark.sanjeevani.utils.toastShort
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.compose.auth.composable.NativeSignInResult
import io.github.jan.supabase.compose.auth.composable.rememberSignInWithGoogle
import io.github.jan.supabase.compose.auth.composeAuth
import org.koin.compose.koinInject


@OptIn(ExperimentalMaterial3ExpressiveApi::class, ExperimentalAnimationApi::class)
@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    supabaseClient: SupabaseClient = koinInject<SupabaseClient>(),
    onSuccessFullAuth: () -> Unit
) {
    var loginRole by remember { mutableStateOf<LoginRole?>(null) }
    var isRoleSelected by remember { mutableStateOf(false) }
    val context = LocalContext.current

    BackHandler(enabled = isRoleSelected) { isRoleSelected = false }

    val action = supabaseClient.composeAuth.rememberSignInWithGoogle(
        onResult = { result ->
            when (result) {
                is NativeSignInResult.Success -> {
                    context.toastLong("Logged-in successfully")
                    onSuccessFullAuth()
                }

                is NativeSignInResult.Error -> {
                    context.toastShort("Failed to login, try again later")
                }

                is NativeSignInResult.NetworkError -> {
                    context.toastShort("Failed to login, check your internet connection")
                }

                NativeSignInResult.ClosedByUser -> {
                    context.toastShort("Failed to login, closed by user")
                }
            }
        }
    )

    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .fillMaxSize()
            .clipToBounds()
            .background(brush = loginScreenGradient)
    ) {
        Image(
            painter = painterResource(R.drawable.login_doc),
            contentDescription = null,
            modifier = Modifier
                .fillMaxSize(0.9f)
                .offset(y = -(100).dp)
                .align(Alignment.TopCenter)
        )
        ElevatedCard(
            elevation = CardDefaults.cardElevation(defaultElevation = 16.dp),
            shape = RoundedCornerShape(topStartPercent = 10, topEndPercent = 10),
            modifier = Modifier
                .animateContentSize()
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
        ) {
            AnimatedContent(
                targetState = isRoleSelected,
                transitionSpec = {
                    if (targetState) {
                        slideInHorizontally { it } + fadeIn() togetherWith slideOutHorizontally { -it } + fadeOut()
                    } else {
                        slideInHorizontally { -it } + fadeIn() togetherWith slideOutHorizontally { +it } + fadeOut()
                    }
                },
                label = "RoleToSectionTransition"
            ) { selected ->
                if (!selected) {
                    LoginRoleSelector(
                        onRoleSelection = { loginRole = it },
                        loginRole = loginRole,
                        onNextClicked = { isRoleSelected = true }
                    )
                } else {
                    LoginSection(
                        loginRole = loginRole,
                        onLoginClicked = { action.startFlow() }
                    )
                }
            }
        }
    }
}
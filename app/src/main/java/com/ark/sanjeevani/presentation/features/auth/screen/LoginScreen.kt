package com.ark.sanjeevani.presentation.features.auth.screen

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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.ark.sanjeevani.R
import com.ark.sanjeevani.presentation.features.auth.components.LoginSection
import com.ark.sanjeevani.presentation.features.auth.logic.UserRole
import com.ark.sanjeevani.utils.toastLong
import com.ark.sanjeevani.utils.toastShort
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.compose.auth.composable.NativeSignInResult
import io.github.jan.supabase.compose.auth.composable.rememberSignInWithGoogle
import io.github.jan.supabase.compose.auth.composeAuth
import org.koin.compose.koinInject


@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    supabaseClient: SupabaseClient = koinInject<SupabaseClient>(),
    onSuccessFullAuth: () -> Unit
) {

    var userRole by remember { mutableStateOf(UserRole.INDIVIDUAL) }
    val context = LocalContext.current

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
            .background(
                brush = Brush.radialGradient(
                    radius = 1000f,
                    colors = listOf(
                        Color(0xFF2D5D46),
                        Color(0xFF377B54),
                        Color(0xFF499B4D)
                    )
                )
            )
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
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
        ) {
            LoginSection(
                onRoleSelection = { userRole = it},
                userRole = userRole,
                onLoginClicked = { action.startFlow() }
            )
        }
    }
}

package com.ark.sanjeevani.presentation.features.auth.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ark.sanjeevani.R
import com.ark.sanjeevani.presentation.features.auth.components.LoginSection
import com.ark.sanjeevani.presentation.features.auth.components.ProfessionSelector
import com.ark.sanjeevani.presentation.features.auth.logic.Profession
import com.ark.sanjeevani.utils.toastLong
import com.ark.sanjeevani.utils.toastShort
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.compose.auth.composable.NativeSignInResult
import io.github.jan.supabase.compose.auth.composable.rememberSignInWithGoogle
import io.github.jan.supabase.compose.auth.composeAuth
import org.koin.compose.koinInject


@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    supabaseClient: SupabaseClient = koinInject<SupabaseClient>(),
    onSuccessFullAuth: () -> Unit
) {

    val profession by remember { mutableStateOf(Profession.USER) }
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

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
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
            .systemBarsPadding()
    ) {
        Box(contentAlignment = Alignment.Center) {
            Image(
                painter = painterResource(R.drawable.login_doc),
                contentDescription = null,
                modifier = Modifier.offset(y = 70.dp)
            )

            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.TopCenter)
            ) {
                Text(
                    text = "Sanjeevani",
                    style = MaterialTheme.typography.headlineLarge,
                    fontWeight = FontWeight.SemiBold
                )
                Text(
                    text = "Your Health Partner",
                    style = MaterialTheme.typography.titleLarge,
                    letterSpacing = 4.sp
                )
            }
        }

        ElevatedCard(
            colors = CardDefaults.elevatedCardColors(
                containerColor = MaterialTheme.colorScheme.onSurface,
                contentColor = MaterialTheme.colorScheme.surface
            ),
            shape = CircleShape,
            modifier = Modifier
                .requiredSize(900.dp)
                .offset(y = 300.dp)
                .padding(16.dp)
                .navigationBarsPadding()
        ) {
            Spacer(Modifier.height(50.dp))
            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxSize()
            ) {
                ProfessionSelector(onProfessionChange = {})
                LoginSection(
                    onLoginClicked = { action.startFlow() }
                )
            }

        }
    }
}
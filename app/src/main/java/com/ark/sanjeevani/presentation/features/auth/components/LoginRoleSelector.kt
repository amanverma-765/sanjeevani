package com.ark.sanjeevani.presentation.features.auth.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.ark.sanjeevani.R
import com.ark.sanjeevani.domain.enums.LoginRole


@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun LoginRoleSelector(
    modifier: Modifier = Modifier,
    loginRole: LoginRole?,
    onRoleSelection: (role: LoginRole) -> Unit,
    onNextClicked: () -> Unit
) {
    Column(
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .fillMaxHeight(.45f)
            .padding(top = 16.dp, start = 16.dp, end = 16.dp)
            .navigationBarsPadding()
    ) {
        AppNameBanner()
        Surface(
            shape = RoundedCornerShape(20),
            color = MaterialTheme.colorScheme.surfaceVariant
        ) {
            Text(
                text = "Select your role to continue",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp)
            )
        }
        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            UserSelectionCard(
                title = "User",
                subTitle = "Track your health\n& consult experts",
                icon = R.drawable.user_icon,
                onClick = { onRoleSelection(LoginRole.INDIVIDUAL) },
                selected = loginRole == LoginRole.INDIVIDUAL,
                modifier = Modifier.weight(1f)
            )

            UserSelectionCard(
                title = "Health Specialist",
                subTitle = "Manage patients\n& share expertise",
                icon = R.drawable.doctor_icon,
                onClick = { onRoleSelection(LoginRole.DOCTOR) },
                selected = loginRole == LoginRole.DOCTOR,
                modifier = Modifier.weight(1f)
            )
        }
        Button(
            onClick = onNextClicked,
            shape = RoundedCornerShape(30),
            enabled = loginRole != null,
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
        ) {
            Text(
                text = when (loginRole) {
                    LoginRole.INDIVIDUAL -> "Continue as User"
                    LoginRole.DOCTOR -> "Continue as Health Specialist"
                    null -> "Select a login Role"
                },
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}
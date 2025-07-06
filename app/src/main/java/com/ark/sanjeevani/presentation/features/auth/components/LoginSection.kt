package com.ark.sanjeevani.presentation.features.auth.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.ark.sanjeevani.R
import com.ark.sanjeevani.presentation.features.auth.logic.UserRole


@Composable
fun LoginSection(
    modifier: Modifier = Modifier,
    onRoleSelection: (role: UserRole) -> Unit,
    userRole: UserRole,
    onLoginClicked: () -> Unit
) {

    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.padding(16.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {

            UserSelectionCard(
                title = "Individual",
                subTitle = "Track your health\n& consult experts",
                icon = R.drawable.user_art,
                onClick = { onRoleSelection(UserRole.INDIVIDUAL) },
                selected = userRole == UserRole.INDIVIDUAL,
                modifier = Modifier.weight(1f)
            )

            UserSelectionCard(
                title = "Health Specialist",
                subTitle = "Manage patients\n& share expertise",
                icon = R.drawable.doctor_art,
                onClick = { onRoleSelection(UserRole.DOCTOR) },
                selected = userRole == UserRole.DOCTOR,
                modifier = Modifier.weight(1f)
            )
        }

        OutlinedButton(
            onClick = onLoginClicked,
            colors = ButtonDefaults.outlinedButtonColors(
                contentColor = MaterialTheme.colorScheme.onSurface,
                containerColor = MaterialTheme.colorScheme.surfaceContainerHigh
            ),
            shape = RoundedCornerShape(40),
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 4.dp),
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(16.dp),
            ) {
                Icon(
                    painter = painterResource(R.drawable.google_icon),
                    contentDescription = "Google icon",
                    modifier = Modifier.size(40.dp),
                    tint = Color.Unspecified
                )
                Text(
                    text = "Continue with Google",
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontWeight = FontWeight.SemiBold
                    )
                )
            }
        }

        Text(
            text = "By continuing, you agree to our Terms of Service and Privacy Policy",
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.bodySmall.copy(
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp)
                .padding(bottom = 16.dp)
        )
    }
}

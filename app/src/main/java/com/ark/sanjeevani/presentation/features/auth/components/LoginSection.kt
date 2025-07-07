package com.ark.sanjeevani.presentation.features.auth.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
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
import com.ark.sanjeevani.presentation.features.auth.logic.LoginRole


@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun LoginSection(
    modifier: Modifier = Modifier,
    loginRole: LoginRole?,
    onLoginClicked: () -> Unit
) {
    val userFeatures = listOf(
        "Connect with Doctors",
        "Book Lab Tests",
        "Medical Records",
        "Book Appointment",
        "Find Nearby Hospitals",
        "Order Medicines"
    )

    val professionalFeatures = listOf(
        "Prescription Management",
        "Video Consultations",
        "Patient Records",
        "Manage Appointments",
        "Billing and Invoicing"
    )

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
                text = "Features we provide",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp)
            )
        }
        FlowRow(
            horizontalArrangement = Arrangement.Center,
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            when (loginRole!!) {
                LoginRole.INDIVIDUAL -> {
                    userFeatures.forEach {
                        NonClickableChip(it)
                    }
                }

                LoginRole.DOCTOR -> {
                    professionalFeatures.forEach {
                        NonClickableChip(it)
                    }
                }
            }
        }
        Text(
            text = "By continuing, you agree to our Terms of Service and Privacy Policy.",
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.bodySmall.copy(
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        )
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
    }
}



package com.ark.sanjeevani.presentation.features.auth.components

import android.R.attr.label
import android.R.attr.onClick
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AssistChip
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.InputChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextFieldDefaults.contentPadding
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.Text
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.ark.sanjeevani.R


@Composable
fun LoginSection(onLoginClicked: () -> Unit) {

    val lightColors = lightColorScheme(MaterialTheme.colorScheme.primary)

    val features = listOf(
        "Book Appointment",
        "Hospitals Nearby",
        "Connect with Doctors"
    )

    FlowRow(
        horizontalArrangement = Arrangement.Center,
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier.width(400.dp)
    ) {
        features.forEach {
            NonClickableChip(it)
        }
    }

    OutlinedButton(
        onClick = onLoginClicked,
        colors = ButtonDefaults.outlinedButtonColors(
            contentColor = lightColors.onSurface,
            containerColor = lightColors.surfaceContainerHigh
        ),
        shape = RoundedCornerShape(48.dp),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 4.dp),
        modifier = Modifier.width(350.dp)
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
            color = lightColors.onSurface.copy(alpha = 0.6f)
        ),
        modifier = Modifier
            .width(350.dp)
            .padding(horizontal = 32.dp)
            .padding(bottom = 16.dp)
    )
}
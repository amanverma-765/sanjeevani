package com.ark.sanjeevani.presentation.features.auth.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Text
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.ark.sanjeevani.R
import com.ark.sanjeevani.presentation.features.auth.logic.Profession


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfessionSelector(
    modifier: Modifier = Modifier,
    onProfessionChange: (Profession) -> Unit
) {
    val lightColors = lightColorScheme(MaterialTheme.colorScheme.primary)
    var selectedIndex by remember { mutableIntStateOf(0) }
    val professions = listOf(Profession.USER, Profession.DOCTOR)

    SingleChoiceSegmentedButtonRow(
        modifier = modifier.sizeIn(maxWidth = 330.dp)
    ) {
        professions.forEachIndexed { index, label ->
            SegmentedButton(
                colors = SegmentedButtonDefaults.colors(
                    inactiveContentColor = lightColors.onSurface,
                    inactiveContainerColor = lightColors.surface,
                ),
                shape = SegmentedButtonDefaults.itemShape(index = index, count = professions.size),
                onClick = {
                    selectedIndex = index
                    onProfessionChange(professions[selectedIndex])
                },
                selected = index == selectedIndex,
                icon = {},
                modifier = Modifier.height(50.dp)
            ) {
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(
                            id = when (label) {
                                Profession.USER -> R.drawable.user
                                Profession.DOCTOR -> R.drawable.doctor
                            }
                        ),
                        contentDescription = null,
                        modifier = Modifier.size(28.dp)
                    )
                    Spacer(Modifier.width(16.dp))
                    Text(
                        text = label.value,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }
        }
    }
}

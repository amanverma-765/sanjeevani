package com.ark.sanjeevani.presentation.features.onBoarding.components

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LanguageTopBar(
    modifier: Modifier = Modifier,
    title: String,
    selected: Boolean,
    onNextClicked: () -> Unit
) {
    TopAppBar(
        modifier = modifier,
        title = {
            Text(
                text = title,
                fontWeight = FontWeight.SemiBold
            )
        },
       actions = {
           FilledIconButton(
               onClick = onNextClicked,
               enabled = selected,
               shape = RoundedCornerShape(40)
           ) {
               Icon(
                   imageVector = Icons.Rounded.Check,
                   contentDescription = null
               )
           }
       }
    )
}
package com.ark.sanjeevani.presentation.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.materialkolor.DynamicMaterialTheme
import com.materialkolor.PaletteStyle

@Composable
fun SanjeevaniTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    seedColor: Color = Color.Green,
    content: @Composable () -> Unit
) {
    DynamicMaterialTheme(
        seedColor = seedColor,
        isDark = darkTheme,
        animate = true,
        style = PaletteStyle.Vibrant,
        typography = AppTypography,
        content = content
    )
}
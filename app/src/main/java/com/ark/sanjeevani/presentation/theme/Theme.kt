package com.ark.sanjeevani.presentation.theme

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.materialkolor.DynamicMaterialTheme

@Composable
fun SanjeevaniTheme(
    darkTheme: Boolean = true,
    seedColor: Color = Color.Cyan,
    content: @Composable () -> Unit
) {
    DynamicMaterialTheme(
        seedColor = seedColor,
        isDark = darkTheme,
        animate = true,
        typography = AppTypography,
        content = content
    )
}
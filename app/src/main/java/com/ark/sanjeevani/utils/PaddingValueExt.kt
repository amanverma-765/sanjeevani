package com.ark.sanjeevani.utils

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.ui.unit.LayoutDirection


operator fun PaddingValues.plus(other: PaddingValues): PaddingValues {
    return PaddingValues(
        start = calculateStartPadding(LayoutDirection.Ltr) + other.calculateStartPadding(LayoutDirection.Ltr),
        top = calculateTopPadding() + other.calculateTopPadding(),
        end = calculateEndPadding(LayoutDirection.Ltr) + other.calculateEndPadding(LayoutDirection.Ltr),
        bottom = calculateBottomPadding() + other.calculateBottomPadding()
    )
}

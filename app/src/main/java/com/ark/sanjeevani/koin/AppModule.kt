package com.ark.sanjeevani.koin

import com.ark.sanjeevani.presentation.features.onBoarding.logic.LocalizationViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val appModule = module {
    viewModelOf(::LocalizationViewModel)
}
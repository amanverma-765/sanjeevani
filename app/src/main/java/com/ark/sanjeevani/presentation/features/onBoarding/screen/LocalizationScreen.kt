package com.ark.sanjeevani.presentation.features.onBoarding.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ark.sanjeevani.presentation.components.TopBarWithCenterText
import com.ark.sanjeevani.presentation.features.onBoarding.components.LanguageCard
import com.ark.sanjeevani.presentation.features.onBoarding.logic.Language
import com.ark.sanjeevani.presentation.features.onBoarding.logic.LocalizationUiEvent
import com.ark.sanjeevani.presentation.features.onBoarding.logic.LocalizationViewModel
import com.ark.sanjeevani.utils.plus
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LocalizationScreen(
    modifier: Modifier = Modifier,
    viewModel: LocalizationViewModel = koinViewModel(),
    onLanguageSelected: (lang: Language) -> Unit
) {

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            TopBarWithCenterText(
                centerText = "Select a Language"
            )
        }
    ) { innerPadding ->
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            contentPadding = innerPadding.plus(PaddingValues(horizontal = 16.dp)),
            modifier = modifier.fillMaxSize()
        ) {
            items(
                items = uiState.languageResp,
                key = { it.id }
            ) {
                LanguageCard(
                    language = it.name,
                    selected = it.selected,
                    onClick = {
                        viewModel.onEvent(
                            LocalizationUiEvent.SelectLanguage(it)
                        )
                        coroutineScope.launch {
                            delay(300)
                            onLanguageSelected(it)
                        }
                    }
                )
            }
        }
    }
}

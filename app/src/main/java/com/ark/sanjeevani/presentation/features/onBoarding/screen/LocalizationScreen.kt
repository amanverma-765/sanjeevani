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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ark.sanjeevani.presentation.features.onBoarding.components.LanguageCard
import com.ark.sanjeevani.presentation.features.onBoarding.components.LanguageTopBar
import com.ark.sanjeevani.presentation.features.onBoarding.logic.LocalizationUiEvent
import com.ark.sanjeevani.presentation.features.onBoarding.logic.LocalizationViewModel
import com.ark.sanjeevani.utils.plus
import org.koin.androidx.compose.koinViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LocalizationScreen(
    modifier: Modifier = Modifier,
    viewModel: LocalizationViewModel = koinViewModel(),
    onLanguageSelected: () -> Unit
) {

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            LanguageTopBar(
                title = "Select a Language",
                onNextClicked = onLanguageSelected,
                selected = uiState.selectedLanguage != null
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
                    }
                )
            }
        }
    }
}

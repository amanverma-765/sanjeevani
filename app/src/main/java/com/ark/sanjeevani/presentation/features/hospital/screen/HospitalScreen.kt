package com.ark.sanjeevani.presentation.features.hospital.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ark.sanjeevani.domain.enums.HospitalType
import com.ark.sanjeevani.presentation.components.FullScreenSearchBar
import com.ark.sanjeevani.presentation.components.SecondaryTopBar
import com.ark.sanjeevani.presentation.features.hospital.components.HospitalCard
import com.ark.sanjeevani.presentation.features.hospital.logic.HospitalUiEvent
import com.ark.sanjeevani.presentation.features.hospital.logic.HospitalViewModel
import org.koin.androidx.compose.koinViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HospitalScreen(
    modifier: Modifier = Modifier,
    viewModel: HospitalViewModel = koinViewModel(),
    type: HospitalType,
    onBackClicked: () -> Unit
) {

    val scrollBehavior = SearchBarDefaults.enterAlwaysSearchBarScrollBehavior()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(true) {
        viewModel.onEvent(HospitalUiEvent.GetHospitals(type))
    }

    Scaffold(
        topBar = {
            Column(
                modifier = modifier.background(
                    TopAppBarDefaults.topAppBarColors().containerColor
                )
            ) {
                SecondaryTopBar(
                    title = "Government Hospitals",
                    onBackClick = onBackClicked
                )
                if (uiState.isLoading) LinearProgressIndicator(Modifier.fillMaxWidth())
                FullScreenSearchBar(scrollBehavior = scrollBehavior)
            }
        }
    ) { innerPadding ->
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
            modifier = modifier
                .fillMaxSize()
                .nestedScroll(scrollBehavior.nestedScrollConnection)
                .padding(innerPadding)
        ) {
            items(
                items = uiState.hospitals,
                key = { it.id }
            ) { hospital ->
                HospitalCard(
                    hospital = hospital,
                    onClick = {

                    }
                )
            }
        }
    }
}
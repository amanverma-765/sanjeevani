package com.ark.sanjeevani.presentation.features.user.hospital.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ark.sanjeevani.domain.enums.HospitalType
import com.ark.sanjeevani.presentation.components.FullScreenSearchBar
import com.ark.sanjeevani.presentation.components.SecondaryTopBar
import com.ark.sanjeevani.presentation.features.user.hospital.components.HospitalCard
import com.ark.sanjeevani.presentation.features.user.hospital.logic.HospitalUiEvent
import com.ark.sanjeevani.presentation.features.user.hospital.logic.HospitalViewModel
import org.koin.androidx.compose.koinViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HospitalListScreen(
    modifier: Modifier = Modifier,
    viewModel: HospitalViewModel = koinViewModel(),
    type: HospitalType,
    onHospitalClicked: (String) -> Unit,
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
                    title = type.text,
                    onBackClick = onBackClicked
                )
                Box(Modifier.height(4.dp)) {
                    if (uiState.isLoading) LinearProgressIndicator(Modifier.fillMaxWidth())
                }
                FullScreenSearchBar(
                    label = "Search ${type.text}",
                    scrollBehavior = scrollBehavior
                )
            }
        }
    ) { innerPadding ->
        LazyColumn(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = modifier
                .fillMaxSize()
                .nestedScroll(scrollBehavior.nestedScrollConnection)
                .padding(innerPadding)
        ) {
            items(
                items = uiState.allHospitals,
                key = { it.id }
            ) { hospital ->
                Column {
                    HospitalCard(
                        hospital = hospital,
                        onClick = {
                            onHospitalClicked(hospital.id)
                        }
                    )
                    if (uiState.allHospitals.last() != hospital)
                        HorizontalDivider(
                            Modifier
                                .padding(horizontal = 12.dp)
                                .alpha(.5f)
                        )
                }
            }
        }
    }
}
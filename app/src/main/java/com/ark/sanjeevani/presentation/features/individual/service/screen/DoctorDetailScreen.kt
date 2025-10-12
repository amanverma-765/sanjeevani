package com.ark.sanjeevani.presentation.features.individual.service.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ark.sanjeevani.presentation.components.FullScreenSearchBar
import com.ark.sanjeevani.presentation.components.SecondaryTopBar
import com.ark.sanjeevani.presentation.features.individual.service.logic.ServiceViewModel
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DoctorDetailScreen(
    modifier: Modifier = Modifier,
    viewModel: ServiceViewModel = koinViewModel(),
    doctorId: String,
    onBackClicked: () -> Unit
) {
    val scrollBehavior = SearchBarDefaults.enterAlwaysSearchBarScrollBehavior()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            Column(
                modifier = modifier.background(
                    TopAppBarDefaults.topAppBarColors().containerColor
                )
            ) {
                SecondaryTopBar(
                    title = "Doctor Detail",
                    onBackClick = onBackClicked
                )
                Box(Modifier.height(4.dp)) {
                    if (uiState.isLoading) LinearProgressIndicator(Modifier.fillMaxWidth())
                }
                FullScreenSearchBar(scrollBehavior = scrollBehavior)
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
        }
    }
}

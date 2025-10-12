package com.ark.sanjeevani.presentation.features.individual.doctor.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ark.sanjeevani.presentation.components.SecondaryTopBar
import com.ark.sanjeevani.presentation.features.individual.doctor.components.DoctorCategoryCard
import com.ark.sanjeevani.presentation.features.individual.doctor.logic.DoctorUiEvent
import com.ark.sanjeevani.presentation.features.individual.doctor.logic.DoctorViewModel
import com.ark.sanjeevani.utils.toastShort
import org.koin.androidx.compose.koinViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DoctorCategoryScreen(
    modifier: Modifier = Modifier,
    viewModel: DoctorViewModel = koinViewModel(),
    onBackClick: () -> Unit,
    onCategoryClick: (id: String, cat: String) -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current
    val scrollBehaviour = TopAppBarDefaults.pinnedScrollBehavior()

    LaunchedEffect(uiState.errorMsg) {
        uiState.errorMsg?.let {
            context.toastShort(it)
            viewModel.onEvent(DoctorUiEvent.ClearError)
        }
    }

    LaunchedEffect(true) {
        viewModel.onEvent(DoctorUiEvent.GetDoctorCategories)
    }

    Scaffold(
        topBar = {
            Column(
                modifier = modifier.background(
                    TopAppBarDefaults.topAppBarColors().containerColor
                )
            ) {
                SecondaryTopBar(
                    title = "Available Doctors",
                    onBackClick = onBackClick,
                    scrollBehavior = scrollBehaviour
                )
                Box(Modifier.height(4.dp)) {
                    if (uiState.isLoading) LinearProgressIndicator(Modifier.fillMaxWidth())
                }
            }
        }
    ) { innerPadding ->
        LazyColumn(
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = modifier
                .fillMaxSize()
                .padding(innerPadding)
                .nestedScroll(scrollBehaviour.nestedScrollConnection),
        ) {
            items(
                items = uiState.doctorCategories,
                key = { it.id + it.name }
            ) {
                DoctorCategoryCard(
                    doctorCategory = it,
                    onClick = {
                        onCategoryClick(it.id, it.name)
                    }
                )
            }
        }
    }
}
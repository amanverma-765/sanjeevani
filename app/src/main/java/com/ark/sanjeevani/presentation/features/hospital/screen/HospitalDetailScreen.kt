package com.ark.sanjeevani.presentation.features.hospital.screen

import android.content.ClipData
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Public
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.ImageLoader
import coil3.compose.AsyncImage
import com.ark.sanjeevani.R
import com.ark.sanjeevani.data.dto.HospitalDto
import com.ark.sanjeevani.domain.model.Hospital
import com.ark.sanjeevani.presentation.components.FullScreenSearchBar
import com.ark.sanjeevani.presentation.components.SecondaryTopBar
import com.ark.sanjeevani.presentation.features.hospital.components.HospitalHeadlineCard
import com.ark.sanjeevani.presentation.features.hospital.components.RoomCard
import com.ark.sanjeevani.presentation.features.hospital.logic.HospitalUiEvent
import com.ark.sanjeevani.presentation.features.hospital.logic.HospitalViewModel
import com.ark.sanjeevani.utils.DefaultImageLoader.imageLoader
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HospitalDetailScreen(
    modifier: Modifier = Modifier,
    viewModel: HospitalViewModel = koinViewModel(),
    hospitalId: String,
    onBackClicked: () -> Unit
) {
    val scrollBehavior = SearchBarDefaults.enterAlwaysSearchBarScrollBehavior()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(true) {
        viewModel.onEvent(HospitalUiEvent.GetHospitalById(hospitalId))
        viewModel.onEvent(HospitalUiEvent.GetHospitalsRoms(hospitalId))
    }

    Scaffold(
        topBar = {
            Column(
                modifier = modifier.background(
                    TopAppBarDefaults.topAppBarColors().containerColor
                )
            ) {
                SecondaryTopBar(
                    title = "Hospital Details",
                    onBackClick = onBackClicked
                )
                if (uiState.isLoading) LinearProgressIndicator(Modifier.fillMaxWidth())
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
            uiState.hospital?.let {
                item {
                    HospitalHeadlineCard(hospital = it)
                }
            }

            items(uiState.rooms) { room ->
                RoomCard(
                    room = room,
                    onClick = { /* TODO: Handle room click */ }
                )
            }
        }
    }
}

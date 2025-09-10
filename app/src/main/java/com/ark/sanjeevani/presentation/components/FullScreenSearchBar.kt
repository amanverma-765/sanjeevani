package com.ark.sanjeevani.presentation.components

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExpandedFullScreenSearchBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.SearchBarScrollBehavior
import androidx.compose.material3.SearchBarValue
import androidx.compose.material3.Text
import androidx.compose.material3.TopSearchBar
import androidx.compose.material3.rememberSearchBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FullScreenSearchBar(
    modifier: Modifier = Modifier,
    scrollBehavior: SearchBarScrollBehavior
) {

    val state = rememberSearchBarState()
    val textFieldState = rememberTextFieldState()
    val scope = rememberCoroutineScope()

    val inputField =
        @Composable {
            SearchBarDefaults.InputField(
                modifier = Modifier.fillMaxWidth(),
                searchBarState = state,
                textFieldState = textFieldState,
                onSearch = { scope.launch { state.animateToCollapsed() } },
                placeholder = { Text("Search here...") },
                leadingIcon = {
                    if (state.currentValue == SearchBarValue.Expanded) {
                        IconButton(
                            onClick = { scope.launch { state.animateToCollapsed() } }
                        ) {
                            Icon(Icons.AutoMirrored.Default.ArrowBack, contentDescription = "Back")
                        }
                    } else {
                        Icon(Icons.Default.Search, contentDescription = null)
                    }
                }
            )
        }

    TopSearchBar(
        state = state,
        scrollBehavior = scrollBehavior,
        inputField = inputField,
        windowInsets = WindowInsets(top = 0.dp, bottom = 0.dp, left = 8.dp, right = 8.dp),
        modifier = modifier.fillMaxWidth()
    )

    ExpandedFullScreenSearchBar(
        state = state,
        inputField = inputField
    ) {

    }
}
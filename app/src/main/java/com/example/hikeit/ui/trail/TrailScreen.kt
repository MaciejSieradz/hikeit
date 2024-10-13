package com.example.hikeit.ui.trail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.hikeit.ui.common.LoadingWheel

@Composable
fun TrailRoute(
    viewModel: TrailViewModel = hiltViewModel()
) {
    val uiState by viewModel.state.collectAsStateWithLifecycle()

    TrailScreen(uiState)
}

@Composable
fun TrailScreen(
    uiState: TrailViewState,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .semantics { contentDescription = "Trail Screen" },
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        when {
            uiState.loading -> {
                LoadingWheel(
                    modifier = modifier,
                    contentDesc = "Loading data"
                )
            }

            else -> {
                Text("Mamy to!")
            }
        }
    }
}
package com.ndronina.sample.tradernet.presentation.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.ndronina.sample.tradernet.presentation.TickersViewModel
import com.ndronina.sample.tradernet.presentation.model.UiState

@Composable
fun TickersScreen(viewModel: TickersViewModel) {
    val uiState by viewModel.state.collectAsState(UiState())

    Scaffold { innerPadding ->
        if (uiState.isLoading) {
            LoadingState(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            )
        } else if (!uiState.errorMessage.isNullOrEmpty()) {
            ErrorState(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                errorMessage = requireNotNull(uiState.errorMessage),
                onRetry = viewModel::onRetry
            )
        } else {
            SuccessState(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                data = uiState.data
            )
        }
    }
}

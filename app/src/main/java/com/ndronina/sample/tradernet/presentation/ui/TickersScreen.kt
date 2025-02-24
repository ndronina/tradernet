package com.ndronina.sample.tradernet.presentation.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.ndronina.sample.tradernet.presentation.TickersViewModel
import com.ndronina.sample.tradernet.presentation.model.UiState

@Composable
fun TickersScreen(viewModel: TickersViewModel) {
    val uiState = remember { mutableStateOf(UiState()) }

    LaunchedEffect(Unit) {
        viewModel.state.collect { newState ->
            uiState.value = newState
        }
    }

    Scaffold { innerPadding ->
        if (uiState.value.isLoading) {
            LoadingState(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            )
        } else if (!uiState.value.errorMessage.isNullOrEmpty()) {
            ErrorState(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                errorMessage = requireNotNull(uiState.value.errorMessage),
                onRetry = viewModel::onRetry
            )
        } else {
            SuccessState(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                data = uiState.value.data
            )
        }
    }
}

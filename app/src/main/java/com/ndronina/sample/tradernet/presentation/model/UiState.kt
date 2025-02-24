package com.ndronina.sample.tradernet.presentation.model

data class UiState(
    val data: List<TickerUiModel> = listOf(),
    val isLoading: Boolean = true,
    val errorMessage: String? = null
)
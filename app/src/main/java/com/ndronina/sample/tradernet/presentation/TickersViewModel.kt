package com.ndronina.sample.tradernet.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ndronina.sample.tradernet.data.model.Resource
import com.ndronina.sample.tradernet.domain.model.Ticker
import com.ndronina.sample.tradernet.domain.usecase.GetSampleTickersUseCase
import com.ndronina.sample.tradernet.domain.usecase.ClearSampleTickersUseCase
import com.ndronina.sample.tradernet.presentation.mapper.TickerUiMapper
import com.ndronina.sample.tradernet.presentation.model.TickerUiModel
import com.ndronina.sample.tradernet.presentation.model.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TickersViewModel @Inject constructor(
    private val getSampleTickersUseCase: GetSampleTickersUseCase,
    private val clearSampleTickersUseCase: ClearSampleTickersUseCase,
    private val tickerUiMapper: TickerUiMapper
) : ViewModel() {

    private var currentTickers = mutableMapOf<String, TickerUiModel>()

    private val _state = MutableStateFlow(UiState())
    val state: StateFlow<UiState> = _state

    init {
        observeState()
    }

    private fun observeState() {
        viewModelScope.launch {
            getSampleTickersUseCase().collect(::handleResource)
        }
    }

    private fun handleResource(resource: Resource<Ticker>) {
        when (resource) {
            is Resource.Success -> {
                val data = resource.data
                val isInitial = !currentTickers.containsKey(data.name)
                currentTickers[data.name] = tickerUiMapper.map(data, isInitial)
                _state.value = UiState(
                    data = currentTickers.values.toList(),
                    isLoading = false
                )
            }
            is Resource.Error -> {
                if (currentTickers.isEmpty()) {
                    _state.value = UiState(
                        isLoading = false,
                        errorMessage = resource.message
                    )
                }
            }
        }
    }

    override fun onCleared() {
        clearSampleTickersUseCase()
        super.onCleared()
    }
}
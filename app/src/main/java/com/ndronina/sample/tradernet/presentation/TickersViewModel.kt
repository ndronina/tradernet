package com.ndronina.sample.tradernet.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ndronina.sample.tradernet.domain.model.Ticker
import com.ndronina.sample.tradernet.domain.usecase.GetSampleTickersUseCase
import com.ndronina.sample.tradernet.domain.usecase.ClearSampleTickersUseCase
import com.ndronina.sample.tradernet.presentation.mapper.TickerUiMapper
import com.ndronina.sample.tradernet.presentation.model.TickerUiModel
import com.ndronina.sample.tradernet.presentation.model.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TickersViewModel @Inject constructor(
    private val getSampleTickersUseCase: GetSampleTickersUseCase,
    private val clearSampleTickersUseCase: ClearSampleTickersUseCase,
    private val tickerUiMapper: TickerUiMapper
) : ViewModel() {

    private var currentTickers = mutableMapOf<String, TickerUiModel>()
    private var stateJob: Job? = null

    private val _state = MutableSharedFlow<UiState>(replay = 1)
    val state: Flow<UiState> = _state

    init {
        observeState()
    }

    private fun observeState() {
        stateJob?.cancel()
        stateJob = viewModelScope.launch {
            getSampleTickersUseCase().collect(::handleResult)
        }
    }


    private suspend fun handleResult(result: Result<Ticker>) {
        val data = result.getOrNull() ?: return
        when {
            result.isSuccess -> {
                val current = currentTickers[data.name]
                if (currentTickers.containsKey(data.name) && current != null) {
                    currentTickers[data.name] = tickerUiMapper.map(current, data)
                } else {
                    currentTickers[data.name] = tickerUiMapper.map(data)
                }
                _state.emit(
                    UiState(
                        data = currentTickers.values.toList(),
                        isLoading = false
                    )
                )
            }
            result.isFailure -> {
                if (currentTickers.isEmpty()) {
                    _state.emit(
                        UiState(
                            isLoading = false,
                            errorMessage = result.exceptionOrNull()?.message ?: ""
                        )
                    )
                }
            }
        }
    }

    fun onRetry() {
        viewModelScope.launch {
            clearSampleTickersUseCase()
            _state.emit(UiState())
            observeState()
        }
    }

    override fun onCleared() {
        stateJob?.cancel()
        clearSampleTickersUseCase()
        super.onCleared()
    }
}
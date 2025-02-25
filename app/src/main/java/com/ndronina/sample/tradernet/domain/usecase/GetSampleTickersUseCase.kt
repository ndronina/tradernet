package com.ndronina.sample.tradernet.domain.usecase

import com.ndronina.sample.tradernet.domain.TickerProvider
import com.ndronina.sample.tradernet.domain.TickersRepository
import com.ndronina.sample.tradernet.domain.model.Ticker
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetSampleTickersUseCase @Inject constructor(
    private val repository: TickersRepository
) {
    operator fun invoke(): Flow<Result<Ticker>> {
        repository.connectToSocket(TickerProvider.sampleTickers)
        return repository.observeTickers()
    }
}
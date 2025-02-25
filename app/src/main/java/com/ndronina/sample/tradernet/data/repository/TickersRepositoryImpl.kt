package com.ndronina.sample.tradernet.data.repository

import com.ndronina.sample.tradernet.data.TickerValidator
import com.ndronina.sample.tradernet.data.mapper.TickerDtoMapper
import com.ndronina.sample.tradernet.data.service.WebSocketService
import com.ndronina.sample.tradernet.domain.TickersRepository
import com.ndronina.sample.tradernet.domain.model.Ticker
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.mapNotNull
import javax.inject.Inject
import javax.inject.Singleton

private const val BASE_LOGO_URL = "https://tradernet.com/logos/get-logo-by-ticker?ticker="

@Singleton
class TickersRepositoryImpl @Inject constructor(
    private val webSocketService: WebSocketService,
    private val mapper: TickerDtoMapper,
    private val tickerValidator: TickerValidator
) : TickersRepository {

    override fun observeTickers(): Flow<Result<Ticker>> {
        return webSocketService.tickers.mapNotNull { result ->
            if (result.isSuccess) {
                val data = result.getOrNull() ?: return@mapNotNull null
                if (!tickerValidator.validate(data)) return@mapNotNull null
            }
            result.map { tickerDto ->
                mapper.map(tickerDto, BASE_LOGO_URL)
            }
        }
    }

    override fun connectToSocket(tickers: List<String>) {
        webSocketService.connect(tickers)
    }

    override fun disconnectFromSocket() {
        webSocketService.disconnect()
    }
}
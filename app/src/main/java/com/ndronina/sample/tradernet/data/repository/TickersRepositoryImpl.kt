package com.ndronina.sample.tradernet.data.repository

import com.ndronina.sample.tradernet.data.mapper.TickerDtoMapper
import com.ndronina.sample.tradernet.data.model.Resource
import com.ndronina.sample.tradernet.data.model.TickerDto
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
    private val mapper: TickerDtoMapper
) : TickersRepository {

    override fun observeTickers(): Flow<Resource<Ticker>> {
        return webSocketService.tickers.mapNotNull { resource ->
            if (resource is Resource.Success && isDataValid(resource.data)) {
                resource.map {
                        tickerDto -> mapper.map(tickerDto, BASE_LOGO_URL)
                }
            } else {
                null
            }
        }
    }

    private fun isDataValid(ticker: TickerDto) =
        !ticker.price.isNaN() && !ticker.priceChange.isNaN() && !ticker.changePercent.isNaN()

    override fun connectToSocket(tickers: List<String>) {
        webSocketService.connect(tickers)
    }

    override fun disconnectFromSocket() {
        webSocketService.disconnect()
    }
}
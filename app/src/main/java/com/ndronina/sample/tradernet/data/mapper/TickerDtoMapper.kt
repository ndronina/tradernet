package com.ndronina.sample.tradernet.data.mapper

import com.ndronina.sample.tradernet.data.model.TickerDto
import com.ndronina.sample.tradernet.domain.model.Ticker
import javax.inject.Inject

class TickerDtoMapper @Inject constructor() {

    fun map(from: TickerDto, logoUrl: String): Ticker {
        return Ticker(
            name = from.name,
            changePercent = from.changePercent,
            stockExchangeName = from.stockExchangeName,
            paperName = from.paperName,
            price = from.price,
            priceChange = from.priceChange,
            companyLogo = mapLogoUrl(logoUrl, from.name)
        )
    }

    private fun mapLogoUrl(logoUrl: String, tickerName: String): String {
        return "$logoUrl${tickerName.lowercase()}"
    }
}
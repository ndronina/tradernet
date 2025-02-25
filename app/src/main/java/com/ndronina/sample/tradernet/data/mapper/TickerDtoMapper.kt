package com.ndronina.sample.tradernet.data.mapper

import com.ndronina.sample.tradernet.data.model.TickerDto
import com.ndronina.sample.tradernet.domain.model.Ticker
import java.math.BigDecimal
import java.math.RoundingMode
import java.text.DecimalFormat
import javax.inject.Inject
import kotlin.math.abs

private const val MAX_DECIMAL_PLACES = 6
private const val NUMBER_FORMAT = "#.######"

class TickerDtoMapper @Inject constructor() {

    fun map(from: TickerDto, logoUrl: String): Ticker {
        return Ticker(
            name = from.name,
            changePercent = from.changePercent,
            stockExchangeName = from.stockExchangeName,
            paperName = from.paperName,
            price = format(roundToMinStep(from.price, from.minStep)),
            priceChange = format(roundToMinStep(from.priceChange, from.minStep)),
            companyLogo = mapLogoUrl(logoUrl, from.name)
        )
    }

    private fun roundToMinStep(value: Double, minStep: Double): Double {
        if (minStep.isNaN()) return value
        val absoluteValue = abs(value)
        val isNegative = value < 0.0

        if (minStep == 0.0) return value
        if (absoluteValue < minStep) return 0.0

        val steps = absoluteValue / minStep
        if (steps % 1.0 == 0.0) return value

        val absoluteResult = kotlin.math.ceil(steps) * minStep
        return if (isNegative) 0.0 - absoluteResult else absoluteResult
    }

    private fun format(value: Double): Double {
        val rounded = BigDecimal.valueOf(value)
            .setScale(MAX_DECIMAL_PLACES, RoundingMode.HALF_UP)
            .stripTrailingZeros()
            .toPlainString()
            .toDouble()
        val formatter = DecimalFormat(NUMBER_FORMAT)
        return formatter.format(rounded).toDouble()
    }

    private fun mapLogoUrl(logoUrl: String, tickerName: String): String {
        return "$logoUrl${tickerName.lowercase()}"
    }
}
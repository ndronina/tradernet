package com.ndronina.sample.tradernet.presentation.mapper

import androidx.compose.ui.graphics.Color
import com.ndronina.sample.tradernet.domain.model.Ticker
import com.ndronina.sample.tradernet.presentation.model.TickerUiModel
import com.ndronina.sample.tradernet.presentation.ui.theme.NegativeColor
import com.ndronina.sample.tradernet.presentation.ui.theme.PositiveColor
import java.text.DecimalFormat
import javax.inject.Inject

class TickerUiMapper @Inject constructor() {

    fun map(from: Ticker, isInitialValue: Boolean = true): TickerUiModel {
        return TickerUiModel(
            imageUrl = from.companyLogo,
            contentDescription = "${from.name} logo",
            leftTitle = from.name,
            leftSubtitle = mapLeftSubtitle(from),
            rightTitle = mapRightTitle(from),
            rightSubtitle = mapRightSubtitle(from),
            rightTitleColor = mapTitleColor(from, isInitialValue),
            rightTitleBackground = mapTitleBackground(from, isInitialValue)
        )
    }

    private fun mapLeftSubtitle(from: Ticker): String {
        return when {
            from.stockExchangeName.isNotEmpty() && from.paperName.isNotEmpty() ->
                "${from.stockExchangeName} | ${from.paperName}"
            from.stockExchangeName.isEmpty() && from.paperName.isNotEmpty() ->
                from.paperName
            from.stockExchangeName.isNotEmpty() && from.paperName.isEmpty() ->
                from.stockExchangeName
            else -> ""
        }
    }

    private fun mapRightTitle(from: Ticker): String {
        val decimalFormat = DecimalFormat("0.00")
        val changePercentText = decimalFormat.format(from.changePercent)
        return when {
            from.changePercent > 0.0 -> "+$changePercentText%"
            from.changePercent < 0.0 -> "$changePercentText%"
            else -> "+0%"
        }
    }

    private fun mapRightSubtitle(from: Ticker): String {
        val priceChangeText = when {
            from.changePercent >= 0.0 -> "+${from.priceChange}"
            else -> "${from.priceChange}"
        }
        return "${from.price} ( $priceChangeText )"
    }

    private fun mapTitleColor(from: Ticker, isInitialValue: Boolean): Color {
        return when {
            from.changePercent == 0.0 -> Color.Black
            isInitialValue && from.changePercent > 0.0 -> PositiveColor
            isInitialValue && from.changePercent < 0.0 -> NegativeColor
            else -> Color.White
        }
    }

    private fun mapTitleBackground(from: Ticker, isInitialValue: Boolean): Color {
        return when {
            from.changePercent == 0.0 || isInitialValue -> Color.Transparent
            from.changePercent > 0.0 -> PositiveColor
            else -> NegativeColor
        }
    }
}
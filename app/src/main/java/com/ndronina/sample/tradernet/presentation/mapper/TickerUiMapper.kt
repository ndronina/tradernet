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
            leftSubtitle = "${from.stockExchangeName} | ${from.paperName}",
            rightTitle = mapRightTitle(from),
            rightSubtitle = mapRightSubtitle(from),
            rightTitleColor = mapTitleColor(from, isInitialValue),
            rightTitleBackground = mapTitleBackground(from, isInitialValue)
        )
    }

    private fun mapRightTitle(from: Ticker): String {
        val decimalFormat = DecimalFormat("0.00")
        val changePercentText = decimalFormat.format(from.changePercent)
        return if (from.changePercent > 0.0) {
            "+$changePercentText%"
        } else if (from.changePercent < 0) {
            "$changePercentText%"
        } else {
            "+0%"
        }
    }

    private fun mapRightSubtitle(from: Ticker): String {
        val priceChangeText = if (from.priceChange >= 0) {
            "+${from.priceChange}"
        }
        else {
            "${from.priceChange}"
        }
        return "${from.price} ( $priceChangeText )"
    }

    private fun mapTitleColor(from: Ticker, isInitialValue: Boolean): Color {
        return if (from.changePercent == 0.0) {
            Color.Black
        } else {
            if (isInitialValue) {
                if (from.changePercent > 0.0) {
                    PositiveColor
                } else {
                    NegativeColor
                }
            } else {
                Color.White
            }
        }
    }

    private fun mapTitleBackground(from: Ticker, isInitialValue: Boolean): Color {
        return if (from.changePercent == 0.0) {
            Color.Transparent
        } else {
            if (isInitialValue) {
                Color.Transparent
            } else {
                if (from.changePercent > 0.0) {
                    PositiveColor
                } else {
                    NegativeColor
                }
            }
        }
    }
}
package com.ndronina.sample.tradernet.data

import com.ndronina.sample.tradernet.data.model.TickerDto
import javax.inject.Inject

class TickerValidator @Inject constructor() {

    fun validate(ticker: TickerDto) =
        !ticker.price.isNaN() && !ticker.priceChange.isNaN() && !ticker.changePercent.isNaN()
}
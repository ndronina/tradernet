package com.ndronina.sample.tradernet.data.service

import com.ndronina.sample.tradernet.data.model.TickerDto
import com.ndronina.sample.tradernet.data.model.TickerParams
import org.json.JSONArray
import javax.inject.Inject

class TickersParser @Inject constructor() {

    fun parse(json: String): TickerDto? {
        return try {
            val jsonArray = JSONArray(json)
            val eventType = jsonArray.optString(0)
            val data = jsonArray.optJSONObject(1)
            val name = data.optString(TickerParams.TICKER_PARAM.code)

            if (eventType != EVENT_TYPE || data == null || name.isNullOrEmpty()) return null

            val changePercent = data.optDouble(TickerParams.CHANGE_PERCENT_PARAM.code)
            val stockExchangeName = data.optString(TickerParams.STOCK_EXCHANGE_NAME.code)
            val paperName = data.optString(TickerParams.PAPER_NAME_PARAM.code)
            val price = data.optDouble(TickerParams.LAST_TRADE_PRICE_PARAM.code)
            val priceChange = data.optDouble(TickerParams.PRICE_CHANGE_PARAM.code)
            val minStep = data.optDouble(TickerParams.MIN_STEP_PARAM.code)

            TickerDto(
                name = name,
                changePercent = changePercent,
                stockExchangeName = stockExchangeName,
                paperName = paperName,
                price = price,
                priceChange = priceChange,
                minStep = minStep
            )
        } catch (e: Exception) {
            android.util.Log.e(TAG, e.message.toString())
            null
        }
    }

    private companion object {
        const val TAG = "TickersParser"
        const val EVENT_TYPE = "q"

    }
}
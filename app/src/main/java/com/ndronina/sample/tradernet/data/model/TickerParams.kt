package com.ndronina.sample.tradernet.data.model

enum class TickerParams(val code: String) {

    /**
     * Тикер
     */
    TICKER_PARAM("c"),

    /**
     * Изменение в процентах относительно цены закрытия предыдущей торговой сессии
     */
    CHANGE_PERCENT_PARAM("pcp"),

    /**
     * Название биржи последней сделки
     */
    STOCK_EXCHANGE_NAME("ltr"),

    /**
     * Название бумаги
     */
    PAPER_NAME_PARAM("name"),

    /**
     * Цена последней сделки
     */
    LAST_TRADE_PRICE_PARAM("ltp"),

    /**
     * Изменение цены последней сделки в пунктах относительно цены закрытия предыдущей торговой сессии
     */
    PRICE_CHANGE_PARAM("chg"),

    /**
     * Минимальный шаг для цены сделки, требуется для округления значений
     */
    MIN_STEP_PARAM("min_step")
}
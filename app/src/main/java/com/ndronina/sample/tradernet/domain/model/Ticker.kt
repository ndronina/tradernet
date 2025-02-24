package com.ndronina.sample.tradernet.domain.model

/**
 * Модель domain слоя для тикера
 *
 * @property name Тикер
 * @property changePercent Изменение в процентах относительно цены закрытия предыдущей торговой сессии
 * @property stockExchangeName Название биржи последней сделки
 * @property paperName Название бумаги
 * @property price Цена последней сделки
 * @property priceChange Изменение цены последней сделки в пунктах относительно цены закрытия предыдущей торговой сессии
 * @property companyLogo Url на логотип компании
 */
data class Ticker(
    val name: String,
    val changePercent: Double,
    val stockExchangeName: String,
    val paperName: String,
    val price: Double,
    val priceChange: Double,
    val companyLogo: String?
)
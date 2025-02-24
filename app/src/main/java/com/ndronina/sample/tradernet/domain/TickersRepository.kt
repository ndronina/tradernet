package com.ndronina.sample.tradernet.domain

import com.ndronina.sample.tradernet.data.model.Resource
import com.ndronina.sample.tradernet.domain.model.Ticker
import kotlinx.coroutines.flow.Flow

interface TickersRepository {

    fun observeTickers(): Flow<Resource<Ticker>>

    fun connectToSocket(tickers: List<String>)

    fun disconnectFromSocket()
}
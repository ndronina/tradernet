package com.ndronina.sample.tradernet.data.service

import com.ndronina.sample.tradernet.BuildConfig
import com.ndronina.sample.tradernet.data.model.TickerDto
import com.ndronina.sample.tradernet.presentation.latestSharedFlow
import kotlinx.coroutines.flow.Flow
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.WebSocket
import okhttp3.WebSocketListener
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WebSocketService @Inject constructor(
    private val tickersParser: TickersParser
) {

    private val client: OkHttpClient = OkHttpClient.Builder()
        .connectTimeout(CONNECT_TIME_OUT_S, TimeUnit.SECONDS)
        .readTimeout(READ_TIME_OUT_MS, TimeUnit.MILLISECONDS)
        .build()

    private var webSocket: WebSocket? = null

    private val _tickers = latestSharedFlow<Result<TickerDto>>()
    val tickers: Flow<Result<TickerDto>> = _tickers

    fun connect(tickers: List<String>) {
        android.util.Log.d(TAG, CONNECT_MESSAGE)
        val request = Request.Builder()
            .url(BuildConfig.WEBSOCKET_URL)
            .build()

        val tickersFormatted = tickers.joinToString(TICKERS_SEPARATOR) { "\"$it\"" }

        webSocket = client.newWebSocket(request, object : WebSocketListener() {

            override fun onOpen(webSocket: WebSocket, response: Response) {
                webSocket.send("""["$EVENT_NAME", [$tickersFormatted]]""")
            }

            override fun onMessage(webSocket: WebSocket, text: String) {
                tickersParser.parse(text)?.let {
                    _tickers.tryEmit(Result.success(it))
                }
            }

            override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
                android.util.Log.e(TAG, t.localizedMessage ?: t.message.toString())
                _tickers.tryEmit(Result.failure(t))
            }

            override fun onClosing(webSocket: WebSocket, code: Int, reason: String) {
                webSocket.close(CLOSE_CODE, CLOSE_MESSAGE)
            }
        })
    }

    fun disconnect() {
        webSocket?.close(CLOSE_CODE, CLOSE_MESSAGE)
    }

    private companion object {
        const val TAG = "WebSocketService"
        const val EVENT_NAME = "realtimeQuotes"
        const val TICKERS_SEPARATOR = ", "
        const val CONNECT_TIME_OUT_S = 10L
        const val READ_TIME_OUT_MS = 0L
        const val CLOSE_CODE = 1000
        const val CONNECT_MESSAGE = "Connecting"
        const val CLOSE_MESSAGE = "Disconnected"
    }
}
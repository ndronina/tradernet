package com.ndronina.sample.tradernet.presentation

import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow

/**
 * Hot flow, that keeps only the latest emitted value
 * Replacing [StateFlow] when we don't want to declare initial value and make it nullable
 */
fun <T> latestSharedFlow() = MutableSharedFlow<T>(
    replay = 1,
    extraBufferCapacity = 0,
    onBufferOverflow = BufferOverflow.DROP_OLDEST
)
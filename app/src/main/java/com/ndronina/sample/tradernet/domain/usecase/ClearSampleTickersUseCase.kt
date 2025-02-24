package com.ndronina.sample.tradernet.domain.usecase

import com.ndronina.sample.tradernet.domain.TickersRepository
import javax.inject.Inject

class ClearSampleTickersUseCase @Inject constructor(
    private val repository: TickersRepository
) {
    operator fun invoke() {
        repository.disconnectFromSocket()
    }
}
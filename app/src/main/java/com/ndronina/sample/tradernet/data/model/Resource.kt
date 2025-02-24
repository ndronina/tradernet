package com.ndronina.sample.tradernet.data.model

sealed class Resource<out T> {

    data class Success<out T>(
        val data: T
    ) : Resource<T>()

    data class Error(
        val message: String
    ) : Resource<Nothing>()

    inline fun <R> map(transform: (T) -> R): Resource<R> = when (this) {
        is Success -> Success(transform(data))
        is Error -> this
    }
}

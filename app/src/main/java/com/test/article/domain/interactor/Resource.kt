package com.test.article.domain.interactor


data class Resource<out T>(val status: NetworkState, val data: T?, val message: String?) {
    companion object {
        fun <T> success(data: T): Resource<T> = Resource(status = NetworkState.SUCCESS, data = data, message = null)

        fun <T> error(data: T?, message: String): Resource<T> =
            Resource(status = NetworkState.ERROR, data = data, message = message)

        fun <T> loading(data: T?): Resource<T> = Resource(status = NetworkState.LOADING, data = data, message = null)
    }
}
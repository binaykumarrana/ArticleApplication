package com.test.article.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.test.article.domain.interactor.NetworkState
import com.test.article.domain.repository.ArticleRepository
import com.test.article.persistence.ArticleDB
import kotlinx.coroutines.*

class ArticleDataSource(
    private val articleRepository: ArticleRepository,
    private val scope: CoroutineScope
) : PageKeyedDataSource<Int, ArticleDB>() {

    private var supervisorJob = SupervisorJob()
    private val networkState = MutableLiveData<NetworkState>()
    private var retryQuery: (() -> Any)? = null //Keep the last query just in case it fails

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, ArticleDB>
    ) {
        retryQuery = { loadInitial(params, callback) }
        executeQuery {
            callback.onResult(it, null, 2)
        }
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, ArticleDB>) {
        //Not implemented, in case required pass page count to api for more data
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, ArticleDB>) {
        //Not implemented
    }

    override fun invalidate() {
        super.invalidate()
        supervisorJob.cancelChildren()
    }

    fun getNetworkState(): LiveData<NetworkState> =
        networkState

    fun refresh() =
        this.invalidate()

    private fun executeQuery(
        callback: (List<ArticleDB>) -> Unit
    ) {
        networkState.postValue(NetworkState.LOADING)
        scope.launch(getJobErrorHandler() + supervisorJob) {
            val article = articleRepository.getArticlesWithPagination()
            retryQuery = null
            networkState.postValue(NetworkState.SUCCESS)
            callback(article)
        }
    }

    private fun getJobErrorHandler() = CoroutineExceptionHandler { _, _ ->
        networkState.postValue(NetworkState.ERROR)
    }
}

package com.test.article.data

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.test.article.domain.repository.ArticleRepository
import com.test.article.persistence.ArticleDB
import kotlinx.coroutines.CoroutineScope

class ArticleDataSourceFactory(
    private val repository: ArticleRepository,
    private val scope: CoroutineScope
) : DataSource.Factory<Int, ArticleDB>() {

    val source = MutableLiveData<ArticleDataSource>()

    override fun create(): DataSource<Int, ArticleDB> {
        val source = ArticleDataSource(repository, scope)
        this.source.postValue(source)
        return source
    }

    private fun getSource() = source.value

    fun updateQuery() {
        getSource()?.refresh()
    }
}

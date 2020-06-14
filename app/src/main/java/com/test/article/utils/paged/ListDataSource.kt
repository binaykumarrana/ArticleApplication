package com.test.article.utils.paged

import androidx.paging.PositionalDataSource
import com.test.article.persistence.ArticleDB
import java.lang.IndexOutOfBoundsException

class ListDataSource(private val items: List<ArticleDB>) : PositionalDataSource<ArticleDB>() {
    override fun loadInitial(
        params: LoadInitialParams,
        callback: LoadInitialCallback<ArticleDB>
    ) {
        callback.onResult(items, 0, items.size)
    }

    override fun loadRange(params: LoadRangeParams, callback: LoadRangeCallback<ArticleDB>) {
        try {
            val start = params.startPosition
            val end = params.startPosition + params.loadSize
            callback.onResult(items.subList(start, end))
        } catch (iobe: IndexOutOfBoundsException) {
            //Handling this exception due to getting issue during conversion list to paged list
        }
    }
}
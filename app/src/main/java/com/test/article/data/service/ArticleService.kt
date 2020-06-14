package com.test.article.data.service

import com.test.article.domain.model.Article
import com.test.article.domain.model.ArticleDetails
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Path

interface ArticleService {
    @GET("article")
    fun getArticlesAsync(
    ): Deferred<List<Article>>

    @GET("article/{id}")
    fun getArticleAsync(@Path("id") id: Int): Deferred<ArticleDetails>
}
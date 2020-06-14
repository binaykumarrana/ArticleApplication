package com.test.article.domain.repository

import com.test.article.data.service.ArticleService
import com.test.article.domain.model.Article
import com.test.article.domain.model.ArticleDetails
import com.test.article.persistence.ArticleDB
import com.test.article.persistence.ArticleDB.Companion.mapList
import com.test.article.persistence.ArticleDao


class ArticleRepository(
    private val articleService: ArticleService,
    private val articleDao: ArticleDao
) {
    private suspend fun getArticles() =
        articleService.getArticlesAsync().await()

    suspend fun getArticlesWithPagination(): List<ArticleDB> {
        val request = getArticles()
            .sortedWith(compareByDescending<Article> { it.date }
                .thenByDescending { it.date })
        //Save mapped list to room DB
        articleDao.insertList(mapList(articleList = request))
        return mapList(articleList = request)
    }

    suspend fun getArticle(articleId: Int): ArticleDetails {
        return articleService.getArticleAsync(articleId).await()
    }

    suspend fun getAllArticlesPersistence(): List<ArticleDB> {
        return articleDao.findAllArticles()
    }
}
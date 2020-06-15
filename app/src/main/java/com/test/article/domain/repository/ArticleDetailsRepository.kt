package com.test.article.domain.repository

import com.test.article.data.service.ArticleService
import com.test.article.domain.model.ArticleDetails
import com.test.article.persistence.ArticleDetailsDao

class ArticleDetailsRepository(
    private val articleService: ArticleService,
    private val articleDao: ArticleDetailsDao
) {

    suspend fun getArticle(articleId: Int): ArticleDetails {
        return articleService.getArticleAsync(articleId).await()
    }

    suspend fun insertArticle(articleDetails: ArticleDetails) {
        return articleDao.insert(articleDetails)
    }

    suspend fun getArticlePersistence(articleId: Int): ArticleDetails{
        return articleDao.findArticleById(articleId)
    }
}
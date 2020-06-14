package com.test.article.persistence

import androidx.room.*

@Dao
interface ArticleDao {

    @Query("SELECT * FROM Article")
    suspend fun findAllArticles(): List<ArticleDB>

    @Query("SELECT count(*) FROM Article")
    suspend fun getArticlesCount(): Int

    @Query("SELECT * FROM Article WHERE articleId = :articleId")
    suspend fun findArticleById(articleId: Int): ArticleDB

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(articleDB: ArticleDB)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertList(articles: List<ArticleDB>)

    @Delete
    suspend fun delete(articleDB: ArticleDB)

    @Query("DELETE FROM Article")
    suspend fun deleteAllArticleData()
}
package com.test.article.persistence

import androidx.room.*
import com.test.article.domain.model.ArticleDetails

@Dao
interface ArticleDetailsDao {

    @Query("SELECT * FROM ArticleDetails WHERE id = :articleId")
    suspend fun findArticleById(articleId: Int): ArticleDetails

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(articleDetails: ArticleDetails)
}
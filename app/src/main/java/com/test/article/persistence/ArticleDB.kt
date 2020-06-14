package com.test.article.persistence

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.test.article.domain.model.Article

@Entity(tableName = "Article")
data class ArticleDB(
    @PrimaryKey val articleId: Int,
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "description") val description: String,
    @ColumnInfo(name = "avatar") val avatarUrl: String,
    @ColumnInfo(name = "date") val date: Long
) {
    companion object {
        private fun map(article: Article): ArticleDB {
            return ArticleDB(
                articleId = article.articleId,
                title = article.title,
                description = article.description,
                avatarUrl = article.avatarUrl,
                date = article.date
            )
        }

        fun mapList(articleList: List<Article>): List<ArticleDB> {
            val articlePostDBList = mutableListOf<ArticleDB>()
            for (article in articleList) {
                articlePostDBList.add(map(article))
            }
            return articlePostDBList
        }
    }
}
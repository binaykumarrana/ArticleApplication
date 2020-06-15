package com.test.article.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "ArticleDetails")
data class ArticleDetails(
    @PrimaryKey val id: Int,
    var text: String
)
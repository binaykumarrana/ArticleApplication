package com.test.article.domain.model

import com.google.gson.annotations.SerializedName

data class Article(
    @SerializedName("id") val articleId: Int,
    @SerializedName("title") val title: String,
    @SerializedName("short_description") val description: String,
    @SerializedName("avatar") val avatarUrl: String,
    @SerializedName("last_update") val date: Long
)
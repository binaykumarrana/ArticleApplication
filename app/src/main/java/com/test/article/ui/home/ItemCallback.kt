package com.test.article.ui.home

interface ItemCallback {
    fun onArticleClicked(id: Int, title: String, avatar: String)
}
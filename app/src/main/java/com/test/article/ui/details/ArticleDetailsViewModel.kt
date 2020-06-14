package com.test.article.ui.details

import androidx.lifecycle.liveData
import com.test.article.domain.interactor.Resource
import com.test.article.domain.repository.ArticleRepository
import com.test.article.ui.BaseViewModel

class ArticleDetailsViewModel(private val articleRepository: ArticleRepository) : BaseViewModel() {
    fun getArticle(articleId: Int) = liveData {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = articleRepository.getArticle(articleId)))
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }
}
package com.test.article.ui.details

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import androidx.paging.PagedList
import com.test.article.domain.interactor.Resource
import com.test.article.domain.model.ArticleDetails
import com.test.article.domain.repository.ArticleDetailsRepository
import com.test.article.persistence.ArticleDB
import com.test.article.ui.BaseViewModel
import com.test.article.utils.paged.getPagedList
import kotlinx.coroutines.launch

class ArticleDetailsViewModel(private val articleRepository: ArticleDetailsRepository) :
    BaseViewModel() {
    fun getArticle(articleId: Int) = liveData {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = articleRepository.getArticle(articleId)))
            articleRepository.insertArticle(articleRepository.getArticle(articleId))
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }

    fun loadArticlePersistence(articleId: Int) = liveData {
        emit(Resource.loading(data = null))
        try {
            val listRetrieved = articleRepository.getArticlePersistence(articleId)
            emit(Resource.success(data = listRetrieved))
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }

    fun updateArticle(article: ArticleDetails) = liveData {
        emit(Resource.loading(data = null))
        try {
            articleRepository.insertArticle(article)
            emit(Resource.success(data = null))
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }
}
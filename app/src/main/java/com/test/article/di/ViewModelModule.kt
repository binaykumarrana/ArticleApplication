package com.test.article.di

import com.test.article.ui.details.ArticleDetailsViewModel
import com.test.article.ui.home.ArticleViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { ArticleViewModel(get()) }
    viewModel { ArticleDetailsViewModel(get()) }
}

package com.test.article.di

import com.test.article.domain.repository.ArticleDetailsRepository
import com.test.article.domain.repository.ArticleRepository
import org.koin.dsl.module

val repositoryModule = module {
    factory { ArticleRepository(get(), get()) }
    factory { ArticleDetailsRepository(get(), get()) }
}
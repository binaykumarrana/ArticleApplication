package com.test.article

import com.test.article.domain.repository.ArticleRepository
import com.test.article.persistence.ArticleDao
import org.koin.dsl.module

fun repoMockedDBModule(dao: ArticleDao) = module {
    factory { ArticleRepository(get(), dao) }
}
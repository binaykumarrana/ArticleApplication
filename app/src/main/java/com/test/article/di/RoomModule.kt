package com.test.article.di

import com.test.article.persistence.AppDataBase
import org.koin.dsl.module

val roomModule = module {
    single { AppDataBase.getInstance(get()) }
    single { get<AppDataBase>().getArticleDao() }
    single { get<AppDataBase>().getArticleDetailsDao() }
}
package com.test.article

import com.test.article.di.viewModelModule
import com.test.article.domain.repository.ArticleRepository
import com.test.article.persistence.ArticleDao
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.koin.core.context.startKoin
import org.koin.test.inject
import org.mockito.Mockito
import retrofit2.HttpException
import java.net.HttpURLConnection

@RunWith(JUnit4::class)
class ArticleRepoTest : BaseMockServerTest() {
    private val articleRepo by inject<ArticleRepository>()
    private var daoMocked = Mockito.mock(ArticleDao::class.java)

    override fun setUp() {
        super.setUp()
        startKoin {
            modules(
                listOf(
                    viewModelModule,
                    networkMockedComponent(mockServer.url("/").toString()),
                    repoMockedDBModule(daoMocked)
                )
            )
        }
    }

    @Test
    fun fetch_article_result_ok() {
        mockHttpResponse("result_article_mocked.json", HttpURLConnection.HTTP_OK)
        runBlocking {
            val articlesListMocked = articleRepo.getArticlesWithPagination()
            Assert.assertNotNull(articlesListMocked)
            Assert.assertEquals(articlesListMocked.isNullOrEmpty(), false)
        }
    }

    @Test
    fun fetch_single_article_result_ok() {
        mockHttpResponse("result_single_article_mocked.json", HttpURLConnection.HTTP_OK)
        runBlocking {
            val articleMocked = articleRepo.getArticle(1)
            Assert.assertNotNull(articleMocked)
            Assert.assertEquals(articleMocked, false)
            Assert.assertEquals(articleMocked.id, 1)
            Assert.assertEquals(
                articleMocked.text,
                "She wholly fat who window extent either formal. Removing welcomed civility or hastened is."
            )
        }
    }

    @Test(expected = HttpException::class)
    fun fetch_articles_result_error() {
        mockHttpResponse("result_article_mocked.json", HttpURLConnection.HTTP_BAD_REQUEST)
        runBlocking {
            val articleListMocked = articleRepo.getArticlesWithPagination()
            Assert.assertEquals(articleListMocked.isNullOrEmpty(), true)
        }
    }
}
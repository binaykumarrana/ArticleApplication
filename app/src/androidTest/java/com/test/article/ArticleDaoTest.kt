package com.test.article

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.test.article.persistence.AppDataBase
import com.test.article.persistence.ArticleDB
import com.test.article.persistence.ArticleDao
import io.reactivex.internal.util.NotificationLite
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class ArticleDaoTest {
    private lateinit var articleDao: ArticleDao
    private lateinit var db: AppDataBase
    private lateinit var articleDB: ArticleDB
    private lateinit var articleDB2: ArticleDB

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, AppDataBase::class.java
        ).build()
        articleDao = db.getArticleDao()
        articleDB = ArticleDB(
            1,
            "article 1 title",
            "This is article 1 short description. She wholly fat who window extent either formal. Removing welcomed civility or hastened is.",
            "https://minotar.net/avatar/user2",
            1586404611
        )
        articleDB2 = ArticleDB(
            2,
            "article 2 title",
            "This is article 2 short description. She wholly fat who window extent either formal. Removing welcomed civility or hastened is.",
            "https://minotar.net/avatar/user3",
            1586404611
        )
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun should_Insert_Article_Item() {

        runBlocking {
            articleDao.insert(articleDB)
            val articleDBTest =
                NotificationLite.getValue<ArticleDB>(articleDao.findArticleById(articleDB.articleId))
            Assert.assertEquals(articleDBTest.articleId, articleDB.articleId)
        }
    }

    @Test
    @Throws(Exception::class)
    fun should_Get_All_Articles() {
        runBlocking {
            articleDao.insert(articleDB)
            articleDao.insert(articleDB2)
            val articleDBTest =
                NotificationLite.getValue<List<ArticleDB>>(articleDao.findAllArticles())
            Assert.assertEquals(articleDBTest.size, 2)
        }
    }

    @Test
    @Throws(Exception::class)
    fun should_Replace_Article() {
        runBlocking {
            articleDao.insert(articleDB)
            articleDao.insert(articleDB)
            val articleDBTest =
                NotificationLite.getValue<List<ArticleDB>>(articleDao.findAllArticles())
            Assert.assertEquals(articleDBTest.size, 1)
        }
    }

    @Test
    @Throws(Exception::class)
    fun should_Delete_Article() {
        runBlocking {
            articleDao.insert(articleDB)
            articleDao.delete(articleDB)
            val articleDBTest =
                NotificationLite.getValue<ArticleDB>(articleDao.findArticleById(articleDB.articleId))
            Assert.assertNull(articleDBTest)
        }
    }

    @Test
    @Throws(Exception::class)
    fun should_Delete_All_Article() {
        runBlocking {
            articleDao.insert(articleDB)
            articleDao.deleteAllArticleData()
            Assert.assertEquals(articleDao.getArticlesCount(), 0)
        }
    }
}
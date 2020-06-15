package com.test.article

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.test.article.domain.model.ArticleDetails
import com.test.article.persistence.AppDataBase
import com.test.article.persistence.ArticleDetailsDao
import io.reactivex.internal.util.NotificationLite
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class ArticleDetailsDaoTest {

    private lateinit var articleDao: ArticleDetailsDao
    private lateinit var db: AppDataBase
    private lateinit var articleDB: ArticleDetails
    private lateinit var articleDB2: ArticleDetails

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, AppDataBase::class.java
        ).build()
        articleDao = db.getArticleDetailsDao()
        articleDB = ArticleDetails(
            1,
            "This is article 1 short description. She wholly fat who window extent either formal. Removing welcomed civility or hastened is."
        )
        articleDB2 = ArticleDetails(
            2,
            "This is article 2 short description. She wholly fat who window extent either formal. Removing welcomed civility or hastened is."
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
                NotificationLite.getValue<ArticleDetails>(articleDao.findArticleById(articleDB.id))
            Assert.assertNotNull(articleDBTest)
        }
    }

    @Test
    @Throws(Exception::class)
    fun should_Get_Article_With_ID() {
        runBlocking {
            articleDao.insert(articleDB)
            val articleDBTest =
                NotificationLite.getValue<ArticleDetails>(articleDao.findArticleById(1))
            Assert.assertNotNull(articleDBTest)
            Assert.assertEquals(articleDBTest.text, "This is article 1 short description. She wholly fat who window extent either formal. Removing welcomed civility or hastened is.")
        }
    }
}
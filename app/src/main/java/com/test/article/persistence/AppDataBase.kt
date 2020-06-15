package com.test.article.persistence

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.test.article.constants.Constants.DB_NAME
import com.test.article.constants.Constants.DB_VERSION
import com.test.article.domain.model.ArticleDetails

@Database(
    entities = [ArticleDB::class, ArticleDetails::class],
    version = DB_VERSION,
    exportSchema = false
)
abstract class AppDataBase : RoomDatabase() {
    abstract fun getArticleDao(): ArticleDao
    abstract fun getArticleDetailsDao(): ArticleDetailsDao

    companion object {
        @Volatile
        private var INSTANCE: AppDataBase? = null

        fun getInstance(context: Context): AppDataBase =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: build(context).also { INSTANCE = it }
            }

        private fun build(context: Context) =
            Room.databaseBuilder(context.applicationContext, AppDataBase::class.java, DB_NAME)
                .addMigrations(MIGRATION_1_TO_2)
                .build()

        //In case app has to migrate to a new DBDao
        private val MIGRATION_1_TO_2 = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                //Not implemented
            }
        }
    }
}
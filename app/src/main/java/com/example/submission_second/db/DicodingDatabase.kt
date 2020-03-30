package com.example.submission_second.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.submission_second.db.dao.FavoriteDao
import com.example.submission_second.db.entity.EntityFavorite

@Database(entities = arrayOf(EntityFavorite::class), version = 1)
abstract class DicodingDatabase : RoomDatabase() {
    abstract fun favoriteDao(): FavoriteDao

    companion object {
        private var INSTANCE: DicodingDatabase? = null
        fun buildDatabase(context: Context): DicodingDatabase? {
            if (INSTANCE == null) {
                synchronized(DicodingDatabase::class) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext, DicodingDatabase::class.java,
                        "favorite.db"
                    ).build()
                }
            }
            return INSTANCE
        }
    }
}
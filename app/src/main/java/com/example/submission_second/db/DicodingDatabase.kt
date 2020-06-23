package com.example.submission_second.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.submission_second.db.dao.FavoriteDao
import com.example.submission_second.db.dao.TeamDao
import com.example.submission_second.db.entity.EntityFavorite
import com.example.submission_second.db.entity.EntityTeam

@Database(entities = arrayOf(EntityFavorite::class,EntityTeam::class), version = 1)
abstract class DicodingDatabase : RoomDatabase() {
    abstract fun favoriteDao(): FavoriteDao
    abstract fun teamDao(): TeamDao
    companion object {
        private var INSTANCE: DicodingDatabase? = null
        fun buildDatabase(context: Context): DicodingDatabase? {
            if (INSTANCE == null) {
                synchronized(DicodingDatabase::class) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext, DicodingDatabase::class.java,
                        "Dicoding.db"
                    ).build()
                }
            }
            return INSTANCE
        }
    }
}
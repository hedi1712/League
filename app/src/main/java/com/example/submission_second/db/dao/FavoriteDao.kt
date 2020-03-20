package com.example.submission_second.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.submission_second.db.entity.EntityFavorite

@Dao
interface FavoriteDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertFavorite(favoriteMatch : EntityFavorite)
}
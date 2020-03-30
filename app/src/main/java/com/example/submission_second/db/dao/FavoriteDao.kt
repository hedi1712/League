package com.example.submission_second.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.submission_second.db.entity.EntityFavorite
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single

@Dao
interface FavoriteDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertFavorite(favoriteMatch: EntityFavorite): Single<Long>

    @Query("Select * from favorite_club")
    fun getListClub(): Observable<List<EntityFavorite>>

    @Query("DELETE from favorite_club WHERE idEvent =:idEvent")
    fun deleteMatchEvent(idEvent: String): Completable

}
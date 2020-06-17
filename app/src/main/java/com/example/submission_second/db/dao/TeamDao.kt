package com.example.submission_second.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.submission_second.db.entity.EntityTeam
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single

@Dao
interface TeamDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTeam(team: EntityTeam): Single<Long>

    @Query("Select * from favorite_team")
    fun getTeamFavorite(): Observable<List<EntityTeam>>

    @Query("DELETE from favorite_team WHERE idTeam =:idTeam")
    fun deleteTeamFavorite(idTeam: String): Completable
}
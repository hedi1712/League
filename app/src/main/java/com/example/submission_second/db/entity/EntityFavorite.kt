package com.example.submission_second.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_club")
data class EntityFavorite(
    @PrimaryKey val idEvent: String,
    @ColumnInfo(name = "home_team") val homeTeam: String?,
    @ColumnInfo(name = "away_team") val awayTeam: String?,
    @ColumnInfo(name = "date") val dateMatch: String?,
    @ColumnInfo(name = "home_score") val homeScore: String?,
    @ColumnInfo(name = "away_score") val awayScore: String?,
    @ColumnInfo(name = "menu_id") val menuId: Int
)

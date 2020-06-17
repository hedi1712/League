package com.example.submission_second.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_team")
data class EntityTeam(
    @PrimaryKey val idTeam: String,
    @ColumnInfo(name = "description") val description: String?,
    @ColumnInfo(name = "strbadge") val badge: String?,
    @ColumnInfo(name = "team_name") val name: String?
)
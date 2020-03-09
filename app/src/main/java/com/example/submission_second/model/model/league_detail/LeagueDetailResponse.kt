package com.example.submission_second.model.model.league_detail


import com.google.gson.annotations.SerializedName

data class LeagueDetailResponse(
    @SerializedName("leagues")
    val leagues: List<LeagueDetailData>
)

data class LeagueDetailData(
    @SerializedName("strDescriptionEN")
    val strDescriptionEN: String,
    @SerializedName("strFanart1")
    val strFanart1: String?,
    @SerializedName("strBadge")
    val strBadge: String?
)

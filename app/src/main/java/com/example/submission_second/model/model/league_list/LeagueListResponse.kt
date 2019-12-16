package com.example.submission_second.model.model.league_list

import com.google.gson.annotations.SerializedName

data class LeagueListResponse(
    @SerializedName("leagues")
    val leagues: List<LeagueData>
)

data class LeagueData(
    @SerializedName("idLeague")
    val idLeague: String,
    @SerializedName("strLeague")
    val strLeague: String,
    @SerializedName("strLeagueAlternate")
    val strLeagueAlternate: String,
    @SerializedName("strSport")
    val strSport: String
)
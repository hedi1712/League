package com.example.submission_second.model.model.search_match


import com.google.gson.annotations.SerializedName

data class SearchMatchResponse(
    @SerializedName("event")
    val event: List<SearchMatchData>
)

data class SearchMatchData(
    @SerializedName("strLeague")
    val strLeague: String?,
    @SerializedName("strSport")
    val strSport: String,
    @SerializedName("idEvent")
    val idEvent: String,
    @SerializedName("dateEvent")
    val dateEvent: String?,
    @SerializedName("intAwayScore")
    val intAwayScore: String?,
    @SerializedName("intHomeScore")
    val intHomeScore: String?,
    @SerializedName("strAwayTeam")
    val strAwayTeam: String?,
    @SerializedName("strHomeTeam")
    val strHomeTeam: String?
)

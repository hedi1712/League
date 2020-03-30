package com.example.submission_second.model.model.next_match


import com.google.gson.annotations.SerializedName

data class NextMatchResponse(
    @SerializedName("events")
    val events: List<Event>
)

data class Event(
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
    val strHomeTeam: String?,
    @SerializedName("strLeague")
    val strLeague: String?,
    @SerializedName("strTime")
    val strTime: String?

)

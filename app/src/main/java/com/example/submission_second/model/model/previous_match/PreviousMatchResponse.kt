package com.example.submission_second.model.model.previous_match


import com.google.gson.annotations.SerializedName

data class PreviousMatchResponse(
    @SerializedName("events")
    val events: List<PreviousMatchData>
) {
    data class PreviousMatchData(
        @SerializedName("dateEvent")
        val dateEvent: String,
        @SerializedName("dateEventLocal")
        val dateEventLocal: String,
        @SerializedName("idAwayTeam")
        val idAwayTeam: String,
        @SerializedName("idEvent")
        val idEvent: String,
        @SerializedName("idHomeTeam")
        val idHomeTeam: String,
        @SerializedName("idLeague")
        val idLeague: String,
        @SerializedName("idSoccerXML")
        val idSoccerXML: Any,
        @SerializedName("intAwayScore")
        val intAwayScore: String,
        @SerializedName("intAwayShots")
        val intAwayShots: Any,
        @SerializedName("intHomeScore")
        val intHomeScore: String,
        @SerializedName("intHomeShots")
        val intHomeShots: Any,
        @SerializedName("intRound")
        val intRound: String,
        @SerializedName("intSpectators")
        val intSpectators: Any,
        @SerializedName("strAwayFormation")
        val strAwayFormation: Any,
        @SerializedName("strAwayGoalDetails")
        val strAwayGoalDetails: Any,
        @SerializedName("strAwayLineupDefense")
        val strAwayLineupDefense: Any,
        @SerializedName("strAwayLineupForward")
        val strAwayLineupForward: Any,
        @SerializedName("strAwayLineupGoalkeeper")
        val strAwayLineupGoalkeeper: Any,
        @SerializedName("strAwayLineupMidfield")
        val strAwayLineupMidfield: Any,
        @SerializedName("strAwayLineupSubstitutes")
        val strAwayLineupSubstitutes: Any,
        @SerializedName("strAwayRedCards")
        val strAwayRedCards: Any,
        @SerializedName("strAwayTeam")
        val strAwayTeam: String,
        @SerializedName("strAwayYellowCards")
        val strAwayYellowCards: Any,
        @SerializedName("strBanner")
        val strBanner: Any,
        @SerializedName("strCircuit")
        val strCircuit: Any,
        @SerializedName("strCity")
        val strCity: Any,
        @SerializedName("strCountry")
        val strCountry: Any,
        @SerializedName("strDate")
        val strDate: Any,
        @SerializedName("strDescriptionEN")
        val strDescriptionEN: Any,
        @SerializedName("strEvent")
        val strEvent: String,
        @SerializedName("strEventAlternate")
        val strEventAlternate: String,
        @SerializedName("strFanart")
        val strFanart: Any,
        @SerializedName("strFilename")
        val strFilename: String,
        @SerializedName("strHomeFormation")
        val strHomeFormation: Any,
        @SerializedName("strHomeGoalDetails")
        val strHomeGoalDetails: Any,
        @SerializedName("strHomeLineupDefense")
        val strHomeLineupDefense: Any,
        @SerializedName("strHomeLineupForward")
        val strHomeLineupForward: Any,
        @SerializedName("strHomeLineupGoalkeeper")
        val strHomeLineupGoalkeeper: Any,
        @SerializedName("strHomeLineupMidfield")
        val strHomeLineupMidfield: Any,
        @SerializedName("strHomeLineupSubstitutes")
        val strHomeLineupSubstitutes: Any,
        @SerializedName("strHomeRedCards")
        val strHomeRedCards: Any,
        @SerializedName("strHomeTeam")
        val strHomeTeam: String,
        @SerializedName("strHomeYellowCards")
        val strHomeYellowCards: Any,
        @SerializedName("strLeague")
        val strLeague: String,
        @SerializedName("strLocked")
        val strLocked: String,
        @SerializedName("strMap")
        val strMap: Any,
        @SerializedName("strPoster")
        val strPoster: Any,
        @SerializedName("strResult")
        val strResult: Any,
        @SerializedName("strSeason")
        val strSeason: String,
        @SerializedName("strSport")
        val strSport: String,
        @SerializedName("strTVStation")
        val strTVStation: Any,
        @SerializedName("strThumb")
        val strThumb: Any,
        @SerializedName("strTime")
        val strTime: String,
        @SerializedName("strTimeLocal")
        val strTimeLocal: String,
        @SerializedName("strTweet1")
        val strTweet1: Any,
        @SerializedName("strTweet2")
        val strTweet2: Any,
        @SerializedName("strTweet3")
        val strTweet3: Any,
        @SerializedName("strVideo")
        val strVideo: Any
    )
}
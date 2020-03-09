package com.example.submission_second.model.model.detail_match


import com.google.gson.annotations.SerializedName

data class GetBadgeAwayTeamResponse(
    @SerializedName("teams")
    val teams: List<TeamAway>
)

data class TeamAway(
    @SerializedName("strTeamBadge")
    val strTeamBadge: String
)
package com.example.submission_second.model.model.detail_match


import com.google.gson.annotations.SerializedName

data class GetBadgeHomeTeamResponse(
    @SerializedName("teams")
    val teams: List<TeamHome>
)

data class TeamHome(
    @SerializedName("strTeamBadge")
    val strTeamBadge: String
)
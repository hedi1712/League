package com.example.submission_second.ui.utils

import com.example.submission_second.model.model.league_detail.LeagueDetailData
import com.example.submission_second.model.model.next_match.Event
import com.example.submission_second.model.model.previous_match.PreviousMatchData
import com.example.submission_second.model.model.search_match.SearchMatchData

object FakeData {

    fun nextMatch(): List<Event> {
        return listOf(Event("", "", "", "", "", "", "", ""))
    }

    fun previousMatch(): List<PreviousMatchData> {
        return listOf(PreviousMatchData("", "", "", "", "", "", "", ""))
    }

    fun detailLeague(): List<LeagueDetailData> {
        return listOf(LeagueDetailData("", "", ""))
    }

    fun searchData(): List<SearchMatchData>{
        return listOf(SearchMatchData("","","","","","","",""))
    }
}
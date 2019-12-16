package com.example.submission_second.api

import com.example.submission_second.model.model.league_detail.LeagueDetailData
import com.example.submission_second.model.model.league_detail.LeagueDetailResponse
import com.example.submission_second.model.model.league_list.LeagueListResponse
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("all_leagues.php")
    fun getAllLeagueData(): Observable<LeagueListResponse>

    @GET("lookupleague.php")
    fun getDetailLeagueWithId(@Query("id") id : String): Observable<LeagueDetailResponse>

//    @GET("eventsnextleague.php")
//    fun getNextMatchWithId(@Query("id") id : String): Observable<ListMatchResponse>
//
//    @GET("eventspastleague.php")
//    fun getPreviousMatchWithId(@Query("id") id : String): Observable<ListMatchResponse>
//
//    @GET("lookupevent.php")
//    fun getDetailMatchWithId(@Query("id") id : String): Observable<ListMatchResponse>
//
//    @GET("searchevents.php")
//    fun getSearchMatchWithId(@Query("id") id : String): Observable<ListMatchResponse>
}
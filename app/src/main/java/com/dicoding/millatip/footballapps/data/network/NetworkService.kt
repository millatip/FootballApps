package com.dicoding.millatip.footballapps.data.network

import com.dicoding.millatip.footballapps.data.model.LeagueResponse
import com.dicoding.millatip.footballapps.data.model.MatchResponse
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Query

interface NetworkService {

    @GET("v1/json/1/all_leagues.php")
    fun getLeagueList(): Deferred<LeagueResponse>

    @GET("v1/json/1/eventspastleague.php")
    fun getPreviousMatch(@Query("id") leagueId: String) : Deferred<MatchResponse>

    @GET("v1/json/eventsnextleague.php")
    fun getNextMatch(@Query("id") leagueId: String) : Deferred<MatchResponse>
}
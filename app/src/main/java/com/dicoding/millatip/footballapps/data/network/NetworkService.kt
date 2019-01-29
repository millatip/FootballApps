package com.dicoding.millatip.footballapps.data.network

import com.dicoding.millatip.footballapps.data.model.*
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NetworkService {

    @GET("v1/json/1/all_leagues.php")
    fun getLeagueList(): Deferred<LeagueResponse>

    @GET("v1/json/1/eventspastleague.php")
    fun getPreviousMatch(@Query("id") leagueId: String): Deferred<Response<MatchResponse>>

    @GET("v1/json/1/eventsnextleague.php")
    fun getNextMatch(@Query("id") leagueId: String): Deferred<Response<MatchResponse>>

    @GET("v1/json/1/lookupevent.php")
    fun getMatchDetail(@Query("id") matchId: String): Deferred<Response<MatchResponse>>

    @GET("v1/json/1/lookup_all_teams.php")
    fun getTeamList(@Query("id") leagueId: String) : Deferred<Response<TeamResponse>>

    @GET("v1/json/1/lookupteam.php")
    fun getTeamDetail(@Query("id") teamId: String?): Deferred<Response<TeamResponse>>

    @GET("v1/json/1/lookup_all_players.php")
    fun getPlayerByTeam(@Query("id") teamId: String?): Deferred<PlayerResponse>

    @GET("v1/json/1/searchevents.php")
    fun searchMatch(@Query("e") matchName: String?): Deferred<Response<MatchResponse>>

    @GET("v1/json/1/searchteams.php")
    fun searchTeamName(@Query("t") teamName: String?) : Deferred<Response<TeamResponse>>
}

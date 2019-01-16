package com.dicoding.millatip.footballapps.data.repository.league

import com.dicoding.millatip.footballapps.data.model.League

interface LeagueRepository {
    fun saveLeagueList(leagues: List<League>)
    suspend fun getSoccerLeagueList(): List<League>
    suspend fun getNetworkLeagueList(): List<League>
    suspend fun getLocalLeagueList(): List<League>
}

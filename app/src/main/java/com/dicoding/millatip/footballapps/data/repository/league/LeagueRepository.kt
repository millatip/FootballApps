package com.dicoding.millatip.footballapps.data.repository.league

import com.dicoding.millatip.footballapps.data.model.League

interface LeagueRepository {
    suspend fun getSoccerLeagueList(): List<League>
    suspend fun getNetworkLeagueList(): List<League>
}
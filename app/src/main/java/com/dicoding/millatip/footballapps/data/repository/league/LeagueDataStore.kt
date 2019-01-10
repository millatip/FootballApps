package com.dicoding.millatip.footballapps.data.repository.league

import android.content.Context
import com.dicoding.millatip.footballapps.data.model.League
import com.dicoding.millatip.footballapps.data.network.NetworkService

class LeagueDataStore
constructor(val networkService: NetworkService, val context: Context) : LeagueRepository{
    override suspend fun getSoccerLeagueList(): List<League> {
        return getNetworkLeagueList()
    }

    override suspend fun getNetworkLeagueList(): List<League> {
        return networkService.getLeagueList().await().leagues
            .filter {
                league ->
                league.sport == "Soccer"
            }
    }
}
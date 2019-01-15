package com.dicoding.millatip.footballapps.data.repository.match

import android.content.Context
import com.dicoding.millatip.footballapps.data.model.Match
import com.dicoding.millatip.footballapps.data.network.NetworkService

class MatchDataStore
constructor(private val networkService: NetworkService, val context: Context) : MatchRepository {
    override suspend fun getMatchDetail(matchId: String): Match {
        return networkService.getMatchDetail(matchId).await().events[0]
    }

    override suspend fun getNextMatch(leagueId: String): List<Match> {
        return networkService.getNextMatch(leagueId).await().events
    }

    override suspend fun getPreviousMatch(leagueId: String): List<Match> {
        return networkService.getPreviousMatch(leagueId).await().events
    }

}

package com.dicoding.millatip.footballapps.data.repository.match

import com.dicoding.millatip.footballapps.data.model.Match

interface MatchRepository {
    suspend fun getNextMatch(leagueId : String): List<Match>
    suspend fun getPreviousMatch(leagueId: String): List<Match>
    suspend fun getMatchDetail(matchId: String): Match
}
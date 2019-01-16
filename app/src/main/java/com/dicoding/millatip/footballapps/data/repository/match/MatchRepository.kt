package com.dicoding.millatip.footballapps.data.repository.match

import com.dicoding.millatip.footballapps.data.model.FavoriteMatch
import com.dicoding.millatip.footballapps.data.model.Match

interface MatchRepository {
    suspend fun getNextMatch(leagueId: String): List<Match>
    suspend fun getPreviousMatch(leagueId: String): List<Match>
    suspend fun getMatchDetail(matchId: String): Match
    suspend fun getFavoriteMatches(): List<FavoriteMatch>
    suspend fun isFavorite(matchId: String): Boolean
    fun addToFavorite(match: Match)
    fun removeFromFavorite(matchId: String)
}

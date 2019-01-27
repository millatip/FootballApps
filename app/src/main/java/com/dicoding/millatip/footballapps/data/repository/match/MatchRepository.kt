package com.dicoding.millatip.footballapps.data.repository.match

import com.dicoding.millatip.footballapps.data.model.FavoriteMatch
import com.dicoding.millatip.footballapps.data.model.Match
import com.dicoding.millatip.footballapps.data.model.MatchResponse
import retrofit2.Response

interface MatchRepository {
    suspend fun getNextMatch(leagueId: String): Response<MatchResponse>
    suspend fun getPreviousMatch(leagueId: String): Response<MatchResponse>
    suspend fun getMatchDetail(matchId: String): Response<MatchResponse>
    suspend fun getFavoriteMatches(): List<FavoriteMatch>
    suspend fun isFavorite(matchId: String): Boolean
    fun addToFavorite(match: Match)
    fun removeFromFavorite(matchId: String)
    suspend fun getMatchSearchResult(matchName: String): List<Match>
}

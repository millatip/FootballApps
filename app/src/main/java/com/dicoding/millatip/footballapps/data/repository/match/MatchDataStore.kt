package com.dicoding.millatip.footballapps.data.repository.match

import android.content.Context
import com.dicoding.millatip.footballapps.data.database.database
import com.dicoding.millatip.footballapps.data.model.FavoriteMatch
import com.dicoding.millatip.footballapps.data.model.Match
import com.dicoding.millatip.footballapps.data.model.MatchResponse
import com.dicoding.millatip.footballapps.data.network.NetworkService
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.delete
import org.jetbrains.anko.db.insert
import org.jetbrains.anko.db.select
import retrofit2.Response

class MatchDataStore
constructor(private val networkService: NetworkService, val context: Context) : MatchRepository {
    override suspend fun getMatchSearchResult(matchName: String): Response<MatchResponse> {
        return networkService.searchMatch(matchName).await()
    }

    override suspend fun getFavoriteMatches(): List<FavoriteMatch> {
        var favoriteList: List<FavoriteMatch> = mutableListOf()

        context.database.use {
            val result = select(FavoriteMatch.TABLE_MATCH_FAVORITE)
            favoriteList = result.parseList(classParser())
        }

        return favoriteList
    }

    override suspend fun isFavorite(matchId: String): Boolean {
        var isFavorite = false

        context.database.use {
            val result = select(FavoriteMatch.TABLE_MATCH_FAVORITE).whereArgs("(MATCH_ID = {id})", "id" to matchId)
            val favorite = result.parseList(classParser<FavoriteMatch>())
            if (!favorite.isEmpty()) isFavorite = true
        }

        return isFavorite
    }

    override fun addToFavorite(match: Match) {
        context.database.use {
            insert(FavoriteMatch.TABLE_MATCH_FAVORITE,
                FavoriteMatch.MATCH_ID to match.matchId,
                FavoriteMatch.MATCH_LEAGUE to match.league,
                FavoriteMatch.HOME_TEAM_ID to match.homeTeamId,
                FavoriteMatch.HOME_TEAM to match.homeTeamName,
                FavoriteMatch.AWAY_TEAM_ID to match.awayTeamId,
                FavoriteMatch.AWAY_TEAM to match.awayTeamName,
                FavoriteMatch.MATCH_DATE to match.matchDate,
                FavoriteMatch.MATCH_TIME to match.matchTime,
                FavoriteMatch.HOME_SCORE to match.homeScore,
                FavoriteMatch.AWAY_SCORE to match.awayScore)
        }
    }

    override fun removeFromFavorite(matchId: String) {
        context.database.use {
            delete(FavoriteMatch.TABLE_MATCH_FAVORITE, "(MATCH_ID = {id})", "id" to matchId)
        }
    }

    override suspend fun getMatchDetail(matchId: String): Response <MatchResponse> {
        return networkService.getMatchDetail(matchId).await()
    }

    override suspend fun getNextMatch(leagueId: String): Response<MatchResponse> {
        return networkService.getNextMatch(leagueId).await()
    }

    override suspend fun getPreviousMatch(leagueId: String): Response<MatchResponse> {
        return networkService.getPreviousMatch(leagueId).await()
    }

}

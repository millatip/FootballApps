package com.dicoding.millatip.footballapps.data.repository.team

import com.dicoding.millatip.footballapps.data.model.FavoriteTeam
import com.dicoding.millatip.footballapps.data.model.Team
import com.dicoding.millatip.footballapps.data.model.TeamResponse
import retrofit2.Response

interface TeamRepository {
    suspend fun getTeamList(leagueId: String): Response<TeamResponse>
    suspend fun getTeamDetail(teamId: String?): Response<TeamResponse>
    suspend fun getFavoriteTeamList() : List<FavoriteTeam>
    suspend fun isFavorite(teamId: String) : Boolean
    fun addToFavorite(team: Team)
    fun removeFromFavorite(teamId: String)
    suspend fun getTeamSearchResult(teamName: String): Response<TeamResponse>
}

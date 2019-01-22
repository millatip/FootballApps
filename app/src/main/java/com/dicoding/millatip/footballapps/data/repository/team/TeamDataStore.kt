package com.dicoding.millatip.footballapps.data.repository.team

import android.content.Context
import com.dicoding.millatip.footballapps.data.database.database
import com.dicoding.millatip.footballapps.data.model.FavoriteTeam
import com.dicoding.millatip.footballapps.data.model.Team
import com.dicoding.millatip.footballapps.data.model.TeamResponse
import com.dicoding.millatip.footballapps.data.network.NetworkService
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.delete
import org.jetbrains.anko.db.insert
import org.jetbrains.anko.db.select
import retrofit2.Response

class TeamDataStore
constructor(
    private val networkService: NetworkService,
    val context: Context
) : TeamRepository {

    override suspend fun getFavoriteTeamList(): List<FavoriteTeam> {
        var teamList: List<FavoriteTeam> = mutableListOf()

        context.database.use {
            val result = select(FavoriteTeam.TABLE_TEAM_FAVORITE)
            teamList = result.parseList(classParser())
        }

        return teamList
    }

    override suspend fun isFavorite(teamId: String): Boolean {
        var isFavorite = false

        context.database.use {
            val result = select(FavoriteTeam.TABLE_TEAM_FAVORITE).whereArgs("(TEAM_ID = {id})", "id" to teamId)
            val favorite = result.parseList(classParser<FavoriteTeam>())
            if (!favorite.isEmpty()) isFavorite = true

        }

        return isFavorite
    }

    override fun addToFavorite(team: Team) {
        context.database.use {
            insert(
                FavoriteTeam.TABLE_TEAM_FAVORITE,
                FavoriteTeam.TEAM_ID to team.teamId,
                FavoriteTeam.TEAM_NAME to team.teamName,
                FavoriteTeam.TEAM_BADGE to team.teamBadge
            )
        }
    }

    override fun removeFromFavorite(teamId: String) {
        context.database.use {
            delete(FavoriteTeam.TABLE_TEAM_FAVORITE, "(TEAM_ID = {id})", "id" to teamId)
        }
    }

    override suspend fun getTeamList(leagueId: String): Response<TeamResponse> {
        return networkService.getTeamList(leagueId).await()
    }

    override suspend fun getTeamDetail(teamId: String?): Response<TeamResponse> {
        return networkService.getTeamDetail(teamId).await()
    }
}

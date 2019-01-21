package com.dicoding.millatip.footballapps.data.repository.team

import android.content.Context
import com.dicoding.millatip.footballapps.data.model.Team
import com.dicoding.millatip.footballapps.data.model.TeamResponse
import com.dicoding.millatip.footballapps.data.network.NetworkService
import retrofit2.Response

class TeamDataStore
constructor(
    private val networkService: NetworkService,
    val context: Context
) : TeamRepository {
    override suspend fun getTeamList(leagueId: String): Response<TeamResponse> {
        return networkService.getTeamList(leagueId).await()
    }

    override suspend fun getTeamDetail(teamId: String?): Response<TeamResponse> {
        return networkService.getTeamDetail(teamId).await()
    }
}

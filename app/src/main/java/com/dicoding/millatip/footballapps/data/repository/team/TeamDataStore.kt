package com.dicoding.millatip.footballapps.data.repository.team

import android.content.Context
import com.dicoding.millatip.footballapps.data.model.Team
import com.dicoding.millatip.footballapps.data.network.NetworkService

class TeamDataStore
constructor(
    private val networkService: NetworkService,
    val context: Context
) : TeamRepository {
    override suspend fun getTeamDetail(teamId: String?): Team {
        return networkService.getTeamDetail(teamId).await().teams[0]
    }
}

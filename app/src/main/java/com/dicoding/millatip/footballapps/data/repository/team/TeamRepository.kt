package com.dicoding.millatip.footballapps.data.repository.team

import com.dicoding.millatip.footballapps.data.model.Team
import com.dicoding.millatip.footballapps.data.model.TeamResponse
import retrofit2.Response

interface TeamRepository {
    suspend fun getTeamList(leagueId: String) : Response<TeamResponse>
    suspend fun getTeamDetail(teamId: String?): Response<TeamResponse>
}

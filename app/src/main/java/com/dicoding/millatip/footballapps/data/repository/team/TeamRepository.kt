package com.dicoding.millatip.footballapps.data.repository.team

import com.dicoding.millatip.footballapps.data.model.Team

interface TeamRepository {
    suspend fun getTeamList(leagueId: String) : List<Team>
    suspend fun getTeamDetail(teamId: String?): Team
}

package com.dicoding.millatip.footballapps.data.repository.player

import com.dicoding.millatip.footballapps.data.model.Player

interface PlayerRepository {
    suspend fun getPlayers(teamId: String): List<Player>
}

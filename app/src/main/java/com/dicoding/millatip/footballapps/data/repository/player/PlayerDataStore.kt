package com.dicoding.millatip.footballapps.data.repository.player

import com.dicoding.millatip.footballapps.data.model.Player
import com.dicoding.millatip.footballapps.data.network.NetworkService

class PlayerDataStore
constructor(private val networkService: NetworkService) : PlayerRepository{
    override suspend fun getPlayers(teamId: String): List<Player> {
        return networkService.getPlayerByTeam(teamId).await().player
    }
}

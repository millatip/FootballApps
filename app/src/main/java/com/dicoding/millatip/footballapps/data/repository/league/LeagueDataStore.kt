package com.dicoding.millatip.footballapps.data.repository.league

import android.content.Context
import com.dicoding.millatip.footballapps.data.database.database
import com.dicoding.millatip.footballapps.data.model.League
import com.dicoding.millatip.footballapps.data.network.NetworkService
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.insert
import org.jetbrains.anko.db.select

class LeagueDataStore
constructor(private val networkService: NetworkService, val context: Context) : LeagueRepository {
    override fun saveLeagueList(leagues: List<League>) {
        leagues.forEach {
            context.database.use {
                insert(
                    League.TABLE_LEAGUE,
                    League.LEAGUE_ID to it.leagueId,
                    League.LEAGUE_NAME to it.leagueName,
                    League.LEAGUE_SPORT to it.sport
                )
            }
        }
    }

    override suspend fun getLocalLeagueList(): List<League> {
        var leagues: List<League> = mutableListOf()

        context.database.use {
            val result = select(League.TABLE_LEAGUE)
            leagues = result.parseList(classParser())
        }

        return leagues
    }

    override suspend fun getSoccerLeagueList(): List<League> {
        return if (getLocalLeagueList().isEmpty()) {
            getNetworkLeagueList()
        } else {
            getLocalLeagueList()
        }
    }

    override suspend fun getNetworkLeagueList(): List<League> {
        return networkService.getLeagueList().await().leagues
            .filter { league ->
                league.sport == "Soccer"
            }
    }
}

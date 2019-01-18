package com.dicoding.millatip.footballapps.presentation.ui.teamlist

import com.dicoding.millatip.footballapps.data.repository.league.LeagueRepository
import com.dicoding.millatip.footballapps.data.repository.team.TeamRepository
import com.dicoding.millatip.footballapps.presentation.base.BasePresenter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class TeamListPresenter<V: TeamListContract.View>
constructor(private val leagueRepository: LeagueRepository, private val teamRepository: TeamRepository) : BasePresenter<V>(), TeamListContract.UserInteractionListener<V>{
    override fun getLeagueList() {
        GlobalScope.launch(Dispatchers.Main){
            try {
                val data = leagueRepository.getSoccerLeagueList()
                view?.displayLeagueList(data)
            } catch (e: Exception) {
                view?.displayErrorMessage("Unable to load league data")
            }
        }
    }

    override fun getTeamList() {
        view?.showLoading()
        GlobalScope.launch(Dispatchers.Main){
            try {
                val data = teamRepository.getTeamList(view?.selectedLeague?.leagueId.toString())
                view?.displayTeamList(data)
                view?.hideLoading()
            } catch (e: Exception) {
                view?.hideLoading()
                view?.displayErrorMessage(e.message ?:"Unable to load team data")
            }
        }
    }

}

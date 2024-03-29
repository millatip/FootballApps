package com.dicoding.millatip.footballapps.presentation.ui.teamlist

import com.dicoding.millatip.footballapps.data.repository.league.LeagueRepository
import com.dicoding.millatip.footballapps.data.repository.team.TeamRepository
import com.dicoding.millatip.footballapps.presentation.base.BasePresenter
import com.dicoding.millatip.footballapps.utils.CoroutineContextProvider
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class TeamListPresenter<V : TeamListContract.View>
constructor(
    private val leagueRepository: LeagueRepository,
    private val teamRepository: TeamRepository,
    private val context: CoroutineContextProvider = CoroutineContextProvider()
) :
    BasePresenter<V>(), TeamListContract.UserInteractionListener<V> {

    override fun searchTeam(teamName: String) {
        view?.showLoading()
        if (teamName.isEmpty()) {
            view?.hideLoading()
            view?.displayErrorMessage("It's empty. We are searching for nothing.")
        } else {
            GlobalScope.launch(context.main) {
                try {
                    val data = teamRepository.getTeamSearchResult(teamName)
                    if (data.isSuccessful) {
                        if (data.code() == 200) {
                            view?.hideLoading()
                            view?.displayTeamList(data.body()?.teams ?: mutableListOf())
                        } else {
                            view?.hideLoading()
                            view?.displayErrorMessage("Unable to load team data")
                        }
                    } else {
                        view?.hideLoading()
                        view?.displayErrorMessage("Unable to load team data")
                    }
                } catch (e: Exception) {
                    view?.hideLoading()
                    view?.displayErrorMessage("Unable to load the data")
                }
            }
        }
    }

    override fun getLeagueList() {
        GlobalScope.launch(context.main) {
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
        GlobalScope.launch(context.main) {

            try {
                val data = teamRepository.getTeamList(view?.selectedLeague?.leagueId.toString())
                if (data.isSuccessful) {
                    if (data.code() == 200) {
                        view?.displayTeamList(data.body()?.teams ?: mutableListOf())
                        view?.hideLoading()
                    } else {
                        view?.hideLoading()
                        view?.displayErrorMessage("Unable to load team data")
                    }
                } else {
                    view?.hideLoading()
                    view?.displayErrorMessage("Unable to load team data")
                }
            } catch (e: Exception) {
                view?.hideLoading()
                view?.displayErrorMessage(e.message ?: "Unable to load team data")
            }
        }
    }

}

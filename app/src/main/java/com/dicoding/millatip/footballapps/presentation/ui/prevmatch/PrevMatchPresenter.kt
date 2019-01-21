package com.dicoding.millatip.footballapps.presentation.ui.prevmatch

import com.dicoding.millatip.footballapps.data.repository.league.LeagueRepository
import com.dicoding.millatip.footballapps.data.repository.match.MatchRepository
import com.dicoding.millatip.footballapps.presentation.base.BasePresenter
import com.dicoding.millatip.footballapps.utils.CoroutineContextProvider
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class PrevMatchPresenter<V : PrevMatchContract.View>
constructor(
    private val matchRepository: MatchRepository,
    private val leagueRepository: LeagueRepository,
    private val context: CoroutineContextProvider = CoroutineContextProvider()
) :
    BasePresenter<V>(), PrevMatchContract.UserActionListener<V> {

    override fun getLeagueList() {
        GlobalScope.launch(context.main) {
            try {
                val data = leagueRepository.getSoccerLeagueList()
                view?.displayLeagueList(data)
            } catch (e: Exception) {
                e.printStackTrace()
                view?.hideLoading()
                view?.displayErrorMessage("Failed to get data.")
            }
        }
    }

    override fun getMatchList() {
        view?.showLoading()
        GlobalScope.launch(context.main) {
            try {
                val data = matchRepository.getPreviousMatch(view?.selectedLeague?.leagueId.toString())
                if (data.isSuccessful) {
                    if (data.code() == 200) {
                        view?.displayMatchList(data.body()?.events ?: mutableListOf())
                        view?.hideLoading()
                    } else {
                        view?.hideLoading()
                        view?.displayErrorMessage("Unable to load match data")
                    }
                } else {
                    view?.hideLoading()
                    view?.displayErrorMessage("Unable to load match data")
                }
            } catch (e: Exception) {
                e.printStackTrace()
                view?.hideLoading()
                view?.displayErrorMessage("Failed to get data")
            }
        }
    }

}

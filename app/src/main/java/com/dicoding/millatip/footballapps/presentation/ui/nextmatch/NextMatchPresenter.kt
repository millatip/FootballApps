package com.dicoding.millatip.footballapps.presentation.ui.nextmatch

import com.dicoding.millatip.footballapps.data.repository.league.LeagueRepository
import com.dicoding.millatip.footballapps.data.repository.match.MatchRepository
import com.dicoding.millatip.footballapps.presentation.base.BasePresenter
import com.dicoding.millatip.footballapps.utils.CoroutineContextProvider
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class NextMatchPresenter<V : NextMatchContract.View>
constructor(
    private val matchRepository: MatchRepository,
    private val leagueRepository: LeagueRepository,
    private val context: CoroutineContextProvider = CoroutineContextProvider()
) :
    BasePresenter<V>(), NextMatchContract.UserActionListener<V> {

    override fun getLeagueList() {
        GlobalScope.launch(context.main) {
            try {
                val data = leagueRepository.getSoccerLeagueList()
                view?.displayLeagueList(data)
            } catch (e: Exception) {
                e.printStackTrace()
                view?.displayErrorMessage("NextMatchFragment = NextMatchPresenter.getLeagueList()")
            }
        }
    }

    override fun getMatchList() {
        view?.showLoading()
        val leagueId = view?.selectedLeague?.leagueId
        GlobalScope.launch(context.main) {
            try {
                val data = matchRepository.getNextMatch(leagueId.toString())
                view?.displayMatchList(data)
                view?.hideLoading()
            } catch (e: Exception) {
                e.printStackTrace()
                view?.displayErrorMessage("NextMatchFragment = NextMatchPresenter.getMatchList()")
            }
        }
    }

}

package com.dicoding.millatip.footballapps.presentation.ui.nextmatch

import android.util.Log
import com.dicoding.millatip.footballapps.data.repository.league.LeagueRepository
import com.dicoding.millatip.footballapps.data.repository.match.MatchRepository
import com.dicoding.millatip.footballapps.presentation.base.BasePresenter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.lang.Exception

class NextMatchPresenter<V : NextMatchContract.View>
constructor(private val matchRepository: MatchRepository, private val leagueRepository: LeagueRepository) :
    BasePresenter<V>(), NextMatchContract.UserActionListener<V> {
    override fun getLeagueList() {
        GlobalScope.launch(Dispatchers.Main) {
            try {
                val data = leagueRepository.getSoccerLeagueList()
                view?.displayLeagueList(data)
            } catch (e: Exception) {
                e.printStackTrace()
                Log.i("NextMatchFragment", "NextMatchPresenter.getLeagueList()")
            }
        }
    }

    override fun getMatchList() {
        view?.showLoading()
        val leagueId = view?.selectedLeague?.leagueId
        GlobalScope.launch(Dispatchers.Main) {
            try {
                val data = matchRepository.getNextMatch(leagueId.toString())
                view?.displayMatchList(data)
                view?.hideLoading()
            } catch (e: Exception) {
                e.printStackTrace()
                Log.i("NextMatchFragment", "NextMatchPresenter.getMatchList()")
            }
        }

    }
}
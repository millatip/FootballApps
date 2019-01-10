package com.dicoding.millatip.footballapps.presentation.ui.nextmatch

import com.dicoding.millatip.footballapps.data.repository.league.LeagueRepository
import com.dicoding.millatip.footballapps.data.repository.match.MatchRepository
import com.dicoding.millatip.footballapps.presentation.base.BasePresenter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class NextMatchPresenter <V : NextMatchContract.View>
constructor(private val matchRepository: MatchRepository, private val leagueRepository: LeagueRepository) : BasePresenter<V>(), NextMatchContract.UserActionListener<V>{
    override fun getLeagueList() {
        GlobalScope.launch(Dispatchers.Main){
            val data = leagueRepository.getSoccerLeagueList()
            view?.displayLeagueList(data)
        }
    }

    override fun getMatchList() {
        view?.showLoading()
        val leagueId = view?.selectedLeague?.leagueId
        GlobalScope.launch(Dispatchers.Main){
            val data = matchRepository.getNextMatch(leagueId.toString())
            view?.displayMatchList(data)
            view?.hideLoading()
        }
    }
}
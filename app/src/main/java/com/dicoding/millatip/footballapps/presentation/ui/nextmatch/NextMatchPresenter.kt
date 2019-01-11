package com.dicoding.millatip.footballapps.presentation.ui.nextmatch

import com.dicoding.millatip.footballapps.data.repository.league.LeagueRepository
import com.dicoding.millatip.footballapps.data.repository.match.MatchRepository
import com.dicoding.millatip.footballapps.presentation.base.BasePresenter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.lang.Exception

class NextMatchPresenter <V : NextMatchContract.View>
constructor(private val matchRepository: MatchRepository, private val leagueRepository: LeagueRepository) : BasePresenter<V>(), NextMatchContract.UserActionListener<V>{
    override fun getLeagueList() {
        try {
            GlobalScope.launch(Dispatchers.Main){
                val data = leagueRepository.getSoccerLeagueList()
                view?.displayLeagueList(data)
            }
        }catch (e: Exception){
            e.printStackTrace()
        }
    }

    override fun getMatchList() {
        view?.showLoading()
        val leagueId = view?.selectedLeague?.leagueId
        try {
            GlobalScope.launch(Dispatchers.Main){
                val data = matchRepository.getNextMatch(leagueId.toString())
                view?.displayMatchList(data)
                view?.hideLoading()
            }
        }catch (e:Exception){
            e.printStackTrace()
        }

    }
}
package com.dicoding.millatip.footballapps.presentation.ui.splash

import com.dicoding.millatip.footballapps.data.repository.league.LeagueRepository
import com.dicoding.millatip.footballapps.presentation.base.BasePresenter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class SplashPresenter<V: SplashContract.View>
constructor(private val leagueRepository: LeagueRepository) : BasePresenter<V>(), SplashContract.UserActionListener<V>{
    override fun getLeagueList() {
        GlobalScope.launch(Dispatchers.Main){
            try {
                val data = leagueRepository.getSoccerLeagueList()
                leagueRepository.saveLeagueList(data)
                view?.openActivity()
            } catch (e: Exception) {
                view?.displayErrorMessage("Unable to load league data")
                view?.openActivity()
            }
        }
    }
}

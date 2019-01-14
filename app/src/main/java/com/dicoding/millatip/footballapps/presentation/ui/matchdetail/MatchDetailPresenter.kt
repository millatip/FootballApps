package com.dicoding.millatip.footballapps.presentation.ui.matchdetail

import com.dicoding.millatip.footballapps.data.repository.match.MatchRepository
import com.dicoding.millatip.footballapps.data.repository.team.TeamRepository
import com.dicoding.millatip.footballapps.presentation.base.BasePresenter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MatchDetailPresenter<V: MatchDetailContract.View>
constructor(private val matchRepository: MatchRepository,
            private val teamRepository: TeamRepository)
    : BasePresenter<V>(), MatchDetailContract.UserActionListener<V>{
    override fun getMatchDetail(matchId: String) {
        view?.showLoading()
        try {
            GlobalScope.launch(Dispatchers.Main){
                val data = matchRepository.getMatchDetail(matchId)
                view?.displayMatch(data)
                view?.hideLoading()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun getHomeTeamBadge(teamId: String) {
        try {
            GlobalScope.launch(Dispatchers.Main){
                val data = teamRepository.getTeamDetail(teamId)
                view?.displayHomeBadge(data.teamBadge)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun getAwayTeamBadge(teamId: String) {
        try {
            GlobalScope.launch(Dispatchers.Main){
                val data = teamRepository.getTeamDetail(teamId)
                view?.displayAwayBadge(data.teamBadge)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}
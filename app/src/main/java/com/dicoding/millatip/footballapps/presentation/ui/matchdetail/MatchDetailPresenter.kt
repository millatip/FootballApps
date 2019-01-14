package com.dicoding.millatip.footballapps.presentation.ui.matchdetail

import android.util.Log
import com.dicoding.millatip.footballapps.data.repository.match.MatchRepository
import com.dicoding.millatip.footballapps.data.repository.team.TeamRepository
import com.dicoding.millatip.footballapps.presentation.base.BasePresenter
import com.dicoding.millatip.footballapps.presentation.ui.matchdetail.MatchDetailActivity.Companion.TAG
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MatchDetailPresenter<V: MatchDetailContract.View>
constructor(private val matchRepository: MatchRepository,
            private val teamRepository: TeamRepository)
    : BasePresenter<V>(), MatchDetailContract.UserActionListener<V>{
    override fun getMatchDetail(matchId: String) {
        view?.showLoading()
        GlobalScope.launch(Dispatchers.Main){
            try {
                val data = matchRepository.getMatchDetail(matchId)
                view?.displayMatch(data)
                view?.hideLoading()
            } catch (e: Exception) {
                e.printStackTrace()
                Log.i(TAG, "MatchDetailPresenter.getMatchDetail()")
            }
        }
    }

    override fun getHomeTeamBadge(teamId: String) {
        GlobalScope.launch(Dispatchers.Main){
            try {
                val data = teamRepository.getTeamDetail(teamId)
                view?.displayHomeBadge(data.teamBadge)
            } catch (e: Exception) {
                e.printStackTrace()
                Log.i(TAG, "MatchDetailPresenter.getHomeTeamBadge()")
            }
        }
    }

    override fun getAwayTeamBadge(teamId: String) {
        GlobalScope.launch(Dispatchers.Main){
            try {
                val data = teamRepository.getTeamDetail(teamId)
                view?.displayAwayBadge(data.teamBadge)
            } catch (e: Exception) {
                e.printStackTrace()
                Log.i(TAG, "MatchDetailPresenter.getAwayTeamBadge()")
            }
        }
    }

}
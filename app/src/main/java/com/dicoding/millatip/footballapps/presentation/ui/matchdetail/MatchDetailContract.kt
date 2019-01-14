package com.dicoding.millatip.footballapps.presentation.ui.matchdetail

import com.dicoding.millatip.footballapps.data.model.Match
import com.dicoding.millatip.footballapps.presentation.base.BaseView
import com.dicoding.millatip.footballapps.presentation.base.IBasePresenter

interface MatchDetailContract {
    interface View: BaseView{
        fun showLoading()
        fun hideLoading()
        fun displayMatch(match: Match)
        fun displayErrorMessage(message: String)
        fun displayHomeBadge(teamBadge: String?)
        fun displayAwayBadge(teamBadge: String?)
    }
    interface UserActionListener<V: View>: IBasePresenter<V>{
        fun getMatchDetail(matchId: String)
        fun getHomeTeamBadge(teamId: String)
        fun getAwayTeamBadge(teamId: String)
    }
}
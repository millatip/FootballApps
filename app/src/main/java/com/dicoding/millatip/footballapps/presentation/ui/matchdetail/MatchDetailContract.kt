package com.dicoding.millatip.footballapps.presentation.ui.matchdetail

import com.dicoding.millatip.footballapps.data.model.Match
import com.dicoding.millatip.footballapps.presentation.base.BaseView
import com.dicoding.millatip.footballapps.presentation.base.IBasePresenter

interface MatchDetailContract {

    interface View : BaseView {
        fun showLoading()
        fun hideLoading()
        fun displayMatch(match: Match, favorite: Boolean)
        fun displayErrorMessage(message: String)
        fun displayHomeBadge(teamBadge: String?)
        fun displayAwayBadge(teamBadge: String?)
        fun onAddToFavorite()
        fun onRemoveFromFavorite()
    }

    interface UserActionListener<V : View> : IBasePresenter<V> {
        fun getMatchDetail(matchId: String)
        fun getHomeTeamBadge(teamId: String)
        fun getAwayTeamBadge(teamId: String)
        fun addToFavorite(match: Match)
        fun removeFromFavorite(match: Match)
    }

}

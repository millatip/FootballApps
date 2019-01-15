package com.dicoding.millatip.footballapps.presentation.ui.prevmatch

import com.dicoding.millatip.footballapps.data.model.League
import com.dicoding.millatip.footballapps.data.model.Match
import com.dicoding.millatip.footballapps.presentation.base.BaseView
import com.dicoding.millatip.footballapps.presentation.base.IBasePresenter

interface PrevMatchContract {

    interface View : BaseView {
        var selectedLeague: League
        fun showLoading()
        fun hideLoading()
        fun displayMatchList(events: List<Match>)
        fun displayErrorMessage(message: String)
        fun displayLeagueList(leagues: List<League>)
    }

    interface UserActionListener<V : View> : IBasePresenter<V> {
        fun getMatchList()
        fun getLeagueList()
    }

}

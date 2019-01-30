package com.dicoding.millatip.footballapps.presentation.ui.teamlist

import com.dicoding.millatip.footballapps.data.model.League
import com.dicoding.millatip.footballapps.data.model.Team
import com.dicoding.millatip.footballapps.presentation.base.BaseView
import com.dicoding.millatip.footballapps.presentation.base.IBasePresenter

interface TeamListContract {

    interface View : BaseView {
        var selectedLeague: League
        fun showLoading()
        fun hideLoading()
        fun displayTeamList(teams: List<Team>)
        fun displayErrorMessage(message: String)
        fun displayLeagueList(leagues: List<League>)
    }

    interface UserInteractionListener<V: View>: IBasePresenter<V> {
        fun getLeagueList()
        fun getTeamList()
        fun searchTeam(teamName: String)
    }
}

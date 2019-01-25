package com.dicoding.millatip.footballapps.presentation.ui.teamdetail

import com.dicoding.millatip.footballapps.data.model.Team
import com.dicoding.millatip.footballapps.presentation.base.BaseView
import com.dicoding.millatip.footballapps.presentation.base.IBasePresenter

interface TeamDetailContract {
    interface View:BaseView{
        fun showLoading()
        fun hideLoading()
        fun displayTeam(team : Team)
        fun displayErrorMessage(message: String)
        fun displayFavoriteStatus(favorite: Boolean)
        fun onAddToFavorite()
        fun onRemoveFromFavorite()
    }
    interface UserActionListener<V: View> : IBasePresenter<V>{
        fun getTeamDetail(teamId: String)
        fun addToFavorite(team: Team)
        fun removeFromFavorite(team: Team)
    }
}

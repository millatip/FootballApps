package com.dicoding.millatip.footballapps.presentation.ui.favoriteteam

import com.dicoding.millatip.footballapps.data.model.FavoriteTeam
import com.dicoding.millatip.footballapps.presentation.base.BaseView
import com.dicoding.millatip.footballapps.presentation.base.IBasePresenter

interface FavoriteTeamContract {

    interface View : BaseView{
        fun showLoading()
        fun hideLoading()
        fun displayFavoriteTeamList(teams: List<FavoriteTeam>)
        fun displayErrorMessage(message: String)
    }

    interface UserActionListener<V: View> : IBasePresenter<V>{
        fun getFavoriteTeamList()
    }
}

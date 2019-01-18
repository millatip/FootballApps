package com.dicoding.millatip.footballapps.presentation.ui.teamdetail.player

import com.dicoding.millatip.footballapps.data.model.Player
import com.dicoding.millatip.footballapps.presentation.base.BaseView
import com.dicoding.millatip.footballapps.presentation.base.IBasePresenter

interface TeamPlayerContract {
    interface View : BaseView{
        fun showLoading()
        fun hideLoading()
        fun displayPlayerList(players: List<Player>)
        fun displayErrorMessage(message: String)
    }

    interface UserActionListener<V: View>: IBasePresenter<V>{
        fun getPlayerList(teamId: String?)
    }
}

package com.dicoding.millatip.footballapps.presentation.ui.splash

import com.dicoding.millatip.footballapps.presentation.base.BaseView
import com.dicoding.millatip.footballapps.presentation.base.IBasePresenter

interface SplashContract {
    interface View : BaseView{
        fun openActivity()
        fun displayErrorMessage(message: String)
    }

    interface UserActionListener<V : View> : IBasePresenter<V>{
        fun getLeagueList()
    }
}

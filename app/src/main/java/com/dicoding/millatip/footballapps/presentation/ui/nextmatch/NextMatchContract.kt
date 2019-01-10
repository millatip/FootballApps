package com.dicoding.millatip.footballapps.presentation.ui.nextmatch

import com.dicoding.millatip.footballapps.data.model.Match
import com.dicoding.millatip.footballapps.presentation.base.BaseView
import com.dicoding.millatip.footballapps.presentation.base.IBasePresenter

interface NextMatchContract {

    interface View: BaseView{
        fun showLoading()
        fun hideLoading()
        fun displayMatchList(events: List<Match>)
        fun displayErrorMessage(message: String)
    }

    interface UserActionListener<V : View> : IBasePresenter<V>{
        fun getMatchList()
    }

}
package com.dicoding.millatip.footballapps.presentation.ui.searchmatch

import com.dicoding.millatip.footballapps.data.model.Match
import com.dicoding.millatip.footballapps.presentation.base.BaseView
import com.dicoding.millatip.footballapps.presentation.base.IBasePresenter

interface SearchMatchContract {

    interface View : BaseView {
        fun showLoading()
        fun hideLoading()
        fun displayMatch(matchList: List<Match>)
        fun displayErrorMessage(message: String)
    }

    interface UserInteractionListener<V: View> : IBasePresenter<V>{
        fun searchMatch(matchName: String)
    }
}

package com.dicoding.millatip.footballapps.presentation.ui.favoritematches

import com.dicoding.millatip.footballapps.data.model.FavoriteMatch
import com.dicoding.millatip.footballapps.presentation.base.BaseView
import com.dicoding.millatip.footballapps.presentation.base.IBasePresenter

interface FavoriteMatchContract {

    interface View : BaseView {
        fun showLoading()
        fun hideLoading()
        fun displayFavoriteMatchList(matchList: List<FavoriteMatch>)
        fun displayErrorMessage(message: String)
    }

    interface UserActionListener<V : View> : IBasePresenter<V> {
        fun getFavoriteMatchList()
    }

}

package com.dicoding.millatip.footballapps.presentation.ui.favoritematch

import com.dicoding.millatip.footballapps.data.repository.match.MatchRepository
import com.dicoding.millatip.footballapps.presentation.base.BasePresenter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class FavoriteMatchPresenter<V : FavoriteMatchContract.View>
constructor(private val matchRepository: MatchRepository) : BasePresenter<V>(),
    FavoriteMatchContract.UserActionListener<V> {
    override fun getFavoriteMatchList() {
        view?.showLoading()
        GlobalScope.launch(Dispatchers.Main) {
            try {
                val data = matchRepository.getFavoriteMatches()
                view?.displayFavoriteMatchList(data)
                view?.hideLoading()
            } catch (e: Exception) {
                e.printStackTrace()
                view?.hideLoading()
                view?.displayErrorMessage("Unable to load Favorite matches")
            }
        }
    }
}

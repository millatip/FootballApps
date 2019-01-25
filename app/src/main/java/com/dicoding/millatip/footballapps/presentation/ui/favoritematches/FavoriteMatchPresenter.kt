package com.dicoding.millatip.footballapps.presentation.ui.favoritematches

import com.dicoding.millatip.footballapps.data.repository.match.MatchRepository
import com.dicoding.millatip.footballapps.presentation.base.BasePresenter
import com.dicoding.millatip.footballapps.utils.CoroutineContextProvider
import com.dicoding.millatip.footballapps.utils.EspressoIdlingResource
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class FavoriteMatchPresenter<V : FavoriteMatchContract.View>
constructor(private val matchRepository: MatchRepository,
            private val context: CoroutineContextProvider = CoroutineContextProvider()
) : BasePresenter<V>(),
    FavoriteMatchContract.UserActionListener<V> {
    override fun getFavoriteMatchList() {
        EspressoIdlingResource.increment()
        view?.showLoading()
        GlobalScope.launch(context.main) {
            try {
                val data = matchRepository.getFavoriteMatches()
                view?.displayFavoriteMatchList(data)
                view?.hideLoading()
                if (!EspressoIdlingResource.idlingResource.isIdleNow){
                    EspressoIdlingResource.decrement()
                }
            } catch (e: Exception) {
                e.printStackTrace()
                view?.hideLoading()
                view?.displayErrorMessage("Unable to load Favorite matches")
            }
        }
    }
}

package com.dicoding.millatip.footballapps.presentation.ui.favoriteteam

import com.dicoding.millatip.footballapps.data.repository.team.TeamRepository
import com.dicoding.millatip.footballapps.presentation.base.BasePresenter
import com.dicoding.millatip.footballapps.utils.CoroutineContextProvider
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class FavoriteTeamPresenter<V : FavoriteTeamContract.View>
constructor(
    private val teamRepository: TeamRepository,
    private val context: CoroutineContextProvider = CoroutineContextProvider()
) : BasePresenter<V>(), FavoriteTeamContract.UserActionListener<V> {
    override fun getFavoriteTeamList() {
        view?.showLoading()
        GlobalScope.launch(context.main) {
            try {
                val data = teamRepository.getFavoriteTeamList()
                view?.displayFavoriteTeamList(data)
                view?.hideLoading()
            } catch (e: Exception) {
                view?.hideLoading()
                view?.displayErrorMessage("Unable to load favorite teams data")
            }
        }
    }
}

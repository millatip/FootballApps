package com.dicoding.millatip.footballapps.presentation.ui.teamdetail

import com.dicoding.millatip.footballapps.data.model.Team
import com.dicoding.millatip.footballapps.data.repository.team.TeamRepository
import com.dicoding.millatip.footballapps.presentation.base.BasePresenter
import com.dicoding.millatip.footballapps.utils.CoroutineContextProvider
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class TeamDetailPresenter<V : TeamDetailContract.View>
constructor(private val teamRepository: TeamRepository,
            private val context: CoroutineContextProvider = CoroutineContextProvider()
) : BasePresenter<V>(), TeamDetailContract.UserActionListener<V>{

    override fun addToFavorite(team: Team) {
        teamRepository.addToFavorite(team)
        view?.onAddToFavorite()
    }

    override fun removeFromFavorite(team: Team) {
        teamRepository.removeFromFavorite(team.teamId.toString())
        view?.onRemoveFromFavorite()
    }

    override fun getTeamDetail(teamId: String) {
        view?.showLoading()
        GlobalScope.launch (context.main){
            try {
                val data = teamRepository.getTeamDetail(teamId)
                val favorite = teamRepository.isFavorite(teamId)
                if (data.isSuccessful) {
                    if (data.code() == 200){
                        data.body()?.teams?.get(0)?.let {
                            view?.displayTeam(it)
                            view?.displayFavoriteStatus(favorite)
                            view?.hideLoading()
                        }
                    }else{
                        view?.hideLoading()
                        view?.displayErrorMessage("Get data failed. Check your internet connection")
                    }
                } else {
                    view?.hideLoading()
                    view?.displayErrorMessage("Get data failed. Check your internet connection")
                }
            } catch (e: Exception) {
                view?.hideLoading()
                view?.displayErrorMessage("Unable to load team data")
            }
        }
    }
}

package com.dicoding.millatip.footballapps.presentation.ui.matchdetail

import com.dicoding.millatip.footballapps.data.model.Match
import com.dicoding.millatip.footballapps.data.repository.match.MatchRepository
import com.dicoding.millatip.footballapps.data.repository.team.TeamRepository
import com.dicoding.millatip.footballapps.presentation.base.BasePresenter
import com.dicoding.millatip.footballapps.utils.CoroutineContextProvider
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MatchDetailPresenter<V : MatchDetailContract.View>
constructor(
    private val matchRepository: MatchRepository,
    private val teamRepository: TeamRepository,
    private val context: CoroutineContextProvider = CoroutineContextProvider()
) : BasePresenter<V>(), MatchDetailContract.UserActionListener<V> {

    override fun getMatchDetail(matchId: String) {
        view?.showLoading()
        GlobalScope.launch(context.main) {
            try {
                val data = matchRepository.getMatchDetail(matchId)
                val favorite = matchRepository.isFavorite(matchId)

                if (data.isSuccessful) {
                    if (data.code() == 200){
                        data.body()?.events?.get(0)?.let {
                            view?.displayMatch(it, favorite)
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
                e.printStackTrace()
                view?.hideLoading()
                view?.displayErrorMessage("Get data failed. Check your internet connection")
            }
        }
    }

    override fun getHomeTeamBadge(teamId: String) {
        GlobalScope.launch(context.main) {
            try {
                val data = teamRepository.getTeamDetail(teamId)
                if (data.isSuccessful) {
                    if (data.code() == 200){
                        data.body()?.teams?.get(0)?.let {
                            view?.displayHomeBadge(it.teamBadge)
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
                e.printStackTrace()
            }
        }
    }

    override fun getAwayTeamBadge(teamId: String) {
        GlobalScope.launch(context.main) {
            try {
                val data = teamRepository.getTeamDetail(teamId)
                if (data.isSuccessful) {
                    if (data.code() == 200){
                        data.body()?.teams?.get(0)?.let {
                            view?.displayAwayBadge(it.teamBadge)
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
                e.printStackTrace()
            }
        }
    }

    override fun addToFavorite(match: Match) {
        matchRepository.addToFavorite(match)
        view?.onAddToFavorite()
    }

    override fun removeFromFavorite(match: Match) {
        matchRepository.removeFromFavorite(match.matchId.toString())
        view?.onRemoveFromFavorite()
    }

}

package com.dicoding.millatip.footballapps.presentation.ui.teamdetail.player

import com.dicoding.millatip.footballapps.data.repository.player.PlayerRepository
import com.dicoding.millatip.footballapps.presentation.base.BasePresenter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class TeamPlayerPresenter<V: TeamPlayerContract.View>
constructor(private val playerRepository: PlayerRepository) : BasePresenter<V>(), TeamPlayerContract.UserActionListener<V>{
    override fun getPlayerList(teamId: String?) {
        view?.showLoading()
        GlobalScope.launch(Dispatchers.Main){
            try {
                val data = playerRepository.getPlayers(teamId.toString())
                view?.displayPlayerList(data)
                view?.hideLoading()
            } catch (e: Exception) {
                view?.hideLoading()
                view?.displayErrorMessage("Unable to load player data")
            }
        }
    }
}

package com.dicoding.millatip.footballapps.presentation.ui.teamdetail

import com.dicoding.millatip.footballapps.data.repository.team.TeamRepository
import com.dicoding.millatip.footballapps.presentation.base.BasePresenter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class TeamDetailPresenter<V : TeamDetailContract.View>
constructor(private val teamRepository: TeamRepository) : BasePresenter<V>(), TeamDetailContract.UserActionListener<V>{
    override fun getTeamDetail(teamId: String) {
        GlobalScope.launch (Dispatchers.Main){
            try {
                val data = teamRepository.getTeamDetail(teamId)
                view?.displayTeam(data)
                view?.hideLoading()
            } catch (e: Exception) {
                view?.hideLoading()
                view?.displayErrorMessage("Unable to load team data")
            }
        }
    }
}

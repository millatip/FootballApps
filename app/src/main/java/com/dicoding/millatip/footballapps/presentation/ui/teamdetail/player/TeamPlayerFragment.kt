package com.dicoding.millatip.footballapps.presentation.ui.teamdetail.player


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.dicoding.millatip.footballapps.R
import com.dicoding.millatip.footballapps.data.model.Player
import com.dicoding.millatip.footballapps.data.model.Team
import com.dicoding.millatip.footballapps.presentation.ui.teamdetail.TeamDetailActivity
import com.dicoding.millatip.footballapps.utils.hide
import com.dicoding.millatip.footballapps.utils.show
import kotlinx.android.synthetic.main.fragment_team_player.*
import org.jetbrains.anko.design.snackbar
import org.koin.android.ext.android.inject

class TeamPlayerFragment : Fragment(), TeamPlayerContract.View, TeamDetailActivity.DataListener {

    val presenter: TeamPlayerPresenter<TeamPlayerContract.View> by inject()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_team_player, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        onAttachView()
        val teamActivity = activity as TeamDetailActivity
        teamActivity.setPlayerDataListener(this)
    }
    override fun showLoading() {
        pbTeamPlayer.show()
    }

    override fun hideLoading() {
        pbTeamPlayer.hide()
    }

    override fun displayPlayerList(players: List<Player>) {
        rvPlayerList.adapter = PlayerAdapter(context, players)
        rvPlayerList.layoutManager = LinearLayoutManager(context)
    }

    override fun displayErrorMessage(message: String) {
        pbTeamPlayer.snackbar(message)
    }

    override fun onAttachView() {
        presenter.onAttach(this)
    }

    override fun onDetachView() {
        presenter.onDetach()
    }

    override fun onDataReceived(team: Team) {
        presenter.getPlayerList(team.teamId)
    }

    override fun onDestroyView() {
        onDetachView()
        super.onDestroyView()
    }
}

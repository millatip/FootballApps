package com.dicoding.millatip.footballapps.presentation.ui.teamdetail.overview


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.dicoding.millatip.footballapps.R
import com.dicoding.millatip.footballapps.data.model.Team
import com.dicoding.millatip.footballapps.presentation.ui.teamdetail.TeamDetailActivity
import kotlinx.android.synthetic.main.fragment_overview.*

class OverviewFragment : Fragment(), TeamDetailActivity.DataListener {
    override fun onDataReceived(team: Team) {
        tvOverview.text = team.teamOverview
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_overview, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val teamActivity = activity as TeamDetailActivity
        teamActivity.setTeamDataListener(this)
    }
}

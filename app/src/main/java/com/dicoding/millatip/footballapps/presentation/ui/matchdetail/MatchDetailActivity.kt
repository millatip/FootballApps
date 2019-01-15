package com.dicoding.millatip.footballapps.presentation.ui.matchdetail

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.MenuItem
import com.bumptech.glide.Glide
import com.dicoding.millatip.footballapps.R
import com.dicoding.millatip.footballapps.data.model.Match
import com.dicoding.millatip.footballapps.utils.*
import kotlinx.android.synthetic.main.activity_match_detail.*
import org.jetbrains.anko.design.snackbar
import org.koin.android.ext.android.inject

class MatchDetailActivity : AppCompatActivity(), MatchDetailContract.View {

    private val presenter: MatchDetailPresenter<MatchDetailContract.View> by inject()

    private lateinit var match: Match

    companion object {
        const val EXTRA_MATCH_ID = "matchId"
        const val EXTRA_HOME_TEAM_ID = "homeTeamId"
        const val EXTRA_AWAY_TEAM_ID = "awayTeamId"
        private const val HOME_STRING = "home"
        private const val AWAY_STRING = "away"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_match_detail)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        onAttachView()
    }

    override fun onResume() {
        super.onResume()
        val intent = intent
        presenter.getMatchDetail(intent.getStringExtra(EXTRA_MATCH_ID))
        presenter.getHomeTeamBadge(intent.getStringExtra(EXTRA_HOME_TEAM_ID))
        presenter.getAwayTeamBadge(intent.getStringExtra(EXTRA_AWAY_TEAM_ID))
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun showLoading() {
        pbMatchDetail.show()
    }

    override fun hideLoading() {
        pbMatchDetail.hide()
    }

    override fun displayMatch(match: Match) {
        this.match = match

        val date = dateFormatter(match.matchDate)
        val time = timeFormatter(match.matchTime)

        tvDateTimeDetail.text = toGmtFormat("$date $time")
        tvHomeTeamDetail.text = match.homeTeamName
        tvAwayTeamDetail.text = match.awayTeamName
        tvHomeScore.text = match.homeScore
        tvAwayScore.text = match.awayScore
        tvHomeFormations.text = match.homeFormation
        tvAwayFormations.text = match.awayFormation
        rvHomeGoals.adapter = MatchDetailAdapter(match.homeGoals?.split(";"), HOME_STRING)
        rvHomeGoals.layoutManager = LinearLayoutManager(this)
        rvAwayGoals.adapter = MatchDetailAdapter(match.awayGoals?.split(";"), AWAY_STRING)
        rvAwayGoals.layoutManager = LinearLayoutManager(this)
        rvHomeRC.adapter = MatchDetailAdapter(match.homeRedCards?.split(";"), HOME_STRING)
        rvHomeRC.layoutManager = LinearLayoutManager(this)
        rvAwayRC.adapter = MatchDetailAdapter(match.awayRedCards?.split(";"), AWAY_STRING)
        rvAwayRC.layoutManager = LinearLayoutManager(this)
        rvHomeYC.adapter = MatchDetailAdapter(match.homeYellowCards?.split(";"), HOME_STRING)
        rvHomeYC.layoutManager = LinearLayoutManager(this)
        rvAwayYC.adapter = MatchDetailAdapter(match.awayYellowCards?.split(";"), AWAY_STRING)
        rvAwayYC.layoutManager = LinearLayoutManager(this)
        rvHomeGoalKeepers.adapter = MatchDetailAdapter(match.homeGK?.split(";"), HOME_STRING)
        rvHomeGoalKeepers.layoutManager = LinearLayoutManager(this)
        rvAwayGoalKeepers.adapter = MatchDetailAdapter(match.awayGK?.split(";"), AWAY_STRING)
        rvAwayGoalKeepers.layoutManager = LinearLayoutManager(this)
        rvHomeDefenders.adapter = MatchDetailAdapter(match.homeDefense?.split(";"), HOME_STRING)
        rvHomeDefenders.layoutManager = LinearLayoutManager(this)
        rvAwayDefenders.adapter = MatchDetailAdapter(match.awayDefense?.split(";"), AWAY_STRING)
        rvAwayDefenders.layoutManager = LinearLayoutManager(this)
        rvHomeMidfielders.adapter = MatchDetailAdapter(match.homeMidfield?.split(";"), HOME_STRING)
        rvHomeMidfielders.layoutManager = LinearLayoutManager(this)
        rvAwayMidfielders.adapter = MatchDetailAdapter(match.awayMidfield?.split(";"), AWAY_STRING)
        rvAwayMidfielders.layoutManager = LinearLayoutManager(this)
        rvHomeForwards.adapter = MatchDetailAdapter(match.homeForward?.split(";"), HOME_STRING)
        rvHomeForwards.layoutManager = LinearLayoutManager(this)
        rvAwayForwards.adapter = MatchDetailAdapter(match.awayForward?.split(";"), AWAY_STRING)
        rvAwayForwards.layoutManager = LinearLayoutManager(this)
        rvHomeSubstitues.adapter = MatchDetailAdapter(match.homeSubs?.split(";"), HOME_STRING)
        rvHomeSubstitues.layoutManager = LinearLayoutManager(this)
        rvAwaySubstitutes.adapter = MatchDetailAdapter(match.awaySubs?.split(";"), AWAY_STRING)
        rvAwaySubstitutes.layoutManager = LinearLayoutManager(this)
    }

    override fun displayErrorMessage(message: String) {
        pbMatchDetail.snackbar(message)
    }

    override fun displayHomeBadge(teamBadge: String?) {
        Glide.with(this).load(teamBadge).into(ivHomeTeam)
    }

    override fun displayAwayBadge(teamBadge: String?) {
        Glide.with(this).load(teamBadge).into(ivAwayTeam)
    }

    override fun onAttachView() {
        presenter.onAttach(this)
    }

    override fun onDetachView() {
        presenter.onDetach()
    }

    override fun onDestroy() {
        onDetachView()
        super.onDestroy()
    }

}


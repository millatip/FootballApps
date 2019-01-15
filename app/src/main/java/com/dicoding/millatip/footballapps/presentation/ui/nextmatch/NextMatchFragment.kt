package com.dicoding.millatip.footballapps.presentation.ui.nextmatch


import android.content.Intent
import android.os.Bundle
import android.provider.CalendarContract
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.dicoding.millatip.footballapps.R
import com.dicoding.millatip.footballapps.data.model.League
import com.dicoding.millatip.footballapps.data.model.Match
import com.dicoding.millatip.footballapps.presentation.ui.matchdetail.MatchDetailActivity
import com.dicoding.millatip.footballapps.utils.*
import kotlinx.android.synthetic.main.fragment_next_match.*
import org.jetbrains.anko.design.snackbar
import org.jetbrains.anko.support.v4.onRefresh
import org.jetbrains.anko.support.v4.startActivity
import org.koin.android.ext.android.inject

class NextMatchFragment : Fragment(), NextMatchContract.View {

    val presenter: NextMatchPresenter<NextMatchContract.View> by inject()

    override var selectedLeague: League
        get() = spNextMatchList.selectedItem as League
        set(value) {}

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_next_match, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        onLoadFragment(savedInstanceState)
    }

    private fun onLoadFragment(savedInstance: Bundle?) {
        onAttachView()

        swipeRefreshLayout.setColorSchemeColors(
            ContextCompat.getColor(requireContext(), android.R.color.holo_blue_light),
            ContextCompat.getColor(requireContext(), android.R.color.holo_green_light),
            ContextCompat.getColor(requireContext(), android.R.color.holo_orange_light),
            ContextCompat.getColor(requireContext(), android.R.color.holo_red_light)
        )
        swipeRefreshLayout.onRefresh {
            presenter.getMatchList()
        }

        spNextMatchList.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(p0: AdapterView<*>?) {
            }

            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                selectedLeague = p0?.getItemAtPosition(p2) as League
                presenter.getMatchList()
            }
        }

        presenter.getLeagueList()
    }

    override fun showLoading() {
        pbNextMatch?.show()
    }

    override fun hideLoading() {
        pbNextMatch?.hide()
    }

    override fun displayMatchList(events: List<Match>) {
        swipeRefreshLayout.isRefreshing = false

        val adapter = NextMatchAdapter(events, {
            startActivity<MatchDetailActivity>(
                MatchDetailActivity.EXTRA_MATCH_ID to it.matchId,
                MatchDetailActivity.EXTRA_HOME_TEAM_ID to it.homeTeamId,
                MatchDetailActivity.EXTRA_AWAY_TEAM_ID to it.awayTeamId
            )
        }, {
            val intent = Intent(Intent.ACTION_INSERT)
            intent.type = "vnd.android.cursor.item/event"

            val date = dateFormatter(it.matchDate)
            val time = timeFormatter(it.matchTime)
            val startTime = getTimeMillis("$date $time")
            val endTime = startTime + 1000 * 60 * 60 * 2

            intent.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, startTime)
            intent.putExtra(CalendarContract.EXTRA_EVENT_END_TIME, endTime)
            intent.putExtra(CalendarContract.EXTRA_EVENT_ALL_DAY, false)
            intent.putExtra(CalendarContract.Events.TITLE, it.matchName)

            startActivity(intent)
        })

        rvNextMatch.adapter = adapter
        rvNextMatch.layoutManager = LinearLayoutManager(context)
        adapter.notifyDataSetChanged()

    }

    override fun displayErrorMessage(message: String) {
        rvNextMatch.snackbar(message)
    }

    override fun displayLeagueList(leagues: List<League>) {
        val spinnerAdapter = ArrayAdapter(context, android.R.layout.simple_spinner_item, leagues)
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spNextMatchList.adapter = spinnerAdapter
    }

    override fun onAttachView() {
        presenter.onAttach(this)
    }

    override fun onDetachView() {
        presenter.onDetach()
    }

    override fun onDestroyView() {
        onDetachView()
        super.onDestroyView()
    }


}

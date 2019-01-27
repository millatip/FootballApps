package com.dicoding.millatip.footballapps.presentation.ui.prevmatch


import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Bundle
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
import com.dicoding.millatip.footballapps.utils.EspressoIdlingResource
import com.dicoding.millatip.footballapps.utils.hide
import com.dicoding.millatip.footballapps.utils.show
import kotlinx.android.synthetic.main.fragment_prev_match.*
import org.jetbrains.anko.design.snackbar
import org.jetbrains.anko.support.v4.onRefresh
import org.jetbrains.anko.support.v4.startActivity
import org.koin.android.ext.android.inject


class PrevMatchFragment : Fragment(), PrevMatchContract.View {

    val presenter: PrevMatchPresenter<PrevMatchContract.View> by inject()

    override var selectedLeague: League
        get() = spPrevMatchList?.selectedItem as League
        set(value) {}

    private fun isNetworkAvailable(context: Context?): Boolean {
        val cm = context?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetworkInfo: NetworkInfo?
        activeNetworkInfo = cm.activeNetworkInfo
        return activeNetworkInfo != null && activeNetworkInfo.isConnected
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_prev_match, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        onAttachView()

        swipeRefreshLayout.setColorSchemeColors(
            ContextCompat.getColor(requireContext(), android.R.color.holo_blue_light),
            ContextCompat.getColor(requireContext(), android.R.color.holo_green_light),
            ContextCompat.getColor(requireContext(), android.R.color.holo_orange_light),
            ContextCompat.getColor(requireContext(), android.R.color.holo_red_light)
        )

        swipeRefreshLayout.onRefresh {
            if (isNetworkAvailable(context)) {
                EspressoIdlingResource.increment()
                presenter.getMatchList()
            } else {
                swipeRefreshLayout.isRefreshing = false
                rvPrevMatch.snackbar("No internet connection.")
            }
        }

        spPrevMatchList.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(p0: AdapterView<*>?) {
            }

            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                selectedLeague = p0?.getItemAtPosition(p2) as League
                EspressoIdlingResource.increment()
                presenter.getMatchList()
            }

        }
        EspressoIdlingResource.increment()
        presenter.getLeagueList()

    }

    override fun showLoading() {
        pbPrevMatch.show()
    }

    override fun hideLoading() {
        pbPrevMatch.hide()
    }

    override fun displayMatchList(events: List<Match>) {
        if (!EspressoIdlingResource.idlingResource.isIdleNow){
            EspressoIdlingResource.decrement()
        }
        swipeRefreshLayout.isRefreshing = false
        val adapter = PrevMatchAdapter(events) {
            startActivity<MatchDetailActivity>(
                MatchDetailActivity.EXTRA_MATCH_ID to it.matchId,
                MatchDetailActivity.EXTRA_HOME_TEAM_ID to it.homeTeamId,
                MatchDetailActivity.EXTRA_AWAY_TEAM_ID to it.awayTeamId
            )
        }

        rvPrevMatch.adapter = adapter
        rvPrevMatch.layoutManager = LinearLayoutManager(context)
    }

    override fun displayErrorMessage(message: String) {
        rvPrevMatch.snackbar(message)
    }

    override fun displayLeagueList(leagues: List<League>) {
        if (!EspressoIdlingResource.idlingResource.isIdleNow){
            EspressoIdlingResource.decrement()
        }
        val spinnerAdapter = ArrayAdapter(context, android.R.layout.simple_spinner_item, leagues)
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spPrevMatchList.adapter = spinnerAdapter
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


package com.dicoding.millatip.footballapps.presentation.ui.prevmatch


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter

import com.dicoding.millatip.footballapps.R
import com.dicoding.millatip.footballapps.data.model.League
import com.dicoding.millatip.footballapps.data.model.Match
import com.dicoding.millatip.footballapps.utils.*
import kotlinx.android.synthetic.main.fragment_prev_match.*
import kotlinx.android.synthetic.main.prev_match_list.view.*
import org.jetbrains.anko.design.snackbar
import org.jetbrains.anko.support.v4.onRefresh
import org.koin.android.ext.android.inject


class PrevMatchFragment : Fragment(), PrevMatchContract.View {

    val presenter: PrevMatchPresenter<PrevMatchContract.View> by inject()

    override var selectedLeague: League
        get() = spPrevMatchList.selectedItem as League
        set(value) {}

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
            presenter.getMatchList()
        }

        spPrevMatchList.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
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
        pbPrevMatch.show()
    }

    override fun hideLoading() {
        pbPrevMatch.hide()
    }

    override fun displayMatchList(events: List<Match>) {
        swipeRefreshLayout.isRefreshing = false
        val adapter = PrevMatchAdapter(events)

        rvPrevMatch.adapter = adapter
        rvPrevMatch.layoutManager = LinearLayoutManager(context)
    }

    override fun displayErrorMessage(message: String) {
        rvPrevMatch.snackbar(message)
    }

    override fun displayLeagueList(leagues: List<League>) {
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

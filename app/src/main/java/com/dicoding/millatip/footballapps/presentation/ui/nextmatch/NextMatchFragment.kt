package com.dicoding.millatip.footballapps.presentation.ui.nextmatch


import android.content.Intent
import android.os.Bundle
import android.provider.CalendarContract
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
import kotlinx.android.synthetic.main.fragment_next_match.*
import kotlinx.android.synthetic.main.next_match_list.view.*
import kotlinx.android.synthetic.main.prev_match_list.view.*
import org.jetbrains.anko.design.snackbar
import org.jetbrains.anko.support.v4.onRefresh
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
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_next_match, container, false)
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
            presenter.getLeagueList()
        }

        spNextMatchList.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(p0: AdapterView<*>?) {
            }

            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                selectedLeague = p0?.getItemAtPosition(p2) as League
                presenter.getMatchList()
            }

        }

    }

    override fun showLoading() {
        pbNextMatch.show()
    }

    override fun hideLoading() {
        pbNextMatch.hide()
    }

    override fun displayMatchList(events: List<Match>) {
        swipeRefreshLayout.isRefreshing = false

        val adapter = NextMatchAdapter(events) {
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
        }

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

    internal class NextMatchAdapter(private val matches: List<Match>, val notificationListener: (Match) -> Unit) : RecyclerView.Adapter<NextMatchAdapter.ViewHolder>(){
        override fun onCreateViewHolder(p0: ViewGroup, p1: Int): NextMatchAdapter.ViewHolder = ViewHolder(LayoutInflater.from(p0.context).inflate(R.layout.next_match_list, p0, false))

        override fun getItemCount(): Int = matches.size

        override fun onBindViewHolder(p0: NextMatchAdapter.ViewHolder, p1: Int) {
            p0.bindItem(matches[p1])
        }

        inner class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
            fun bindItem(match: Match){
                val date = dateFormatter(match.matchDate)
                val time = timeFormatter(match.matchTime)

                itemView.tvHomeTeam.text = match.homeTeamName
                itemView.tvAwayTeam.text = match.awayTeamName
                itemView.tvDateTime.text = toGmtFormat("$date $time")
                itemView.ivNotification.setOnClickListener { notificationListener(match) }
            }
        }

    }

}

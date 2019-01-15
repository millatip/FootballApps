package com.dicoding.millatip.footballapps.presentation.ui.prevmatch

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.dicoding.millatip.footballapps.R
import com.dicoding.millatip.footballapps.data.model.Match
import com.dicoding.millatip.footballapps.utils.dateFormatter
import com.dicoding.millatip.footballapps.utils.timeFormatter
import com.dicoding.millatip.footballapps.utils.toGmtFormat
import kotlinx.android.synthetic.main.prev_match_list.view.*

class PrevMatchAdapter(private val matches: List<Match>, val listener: (Match) -> Unit) :
    RecyclerView.Adapter<PrevMatchAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(
        LayoutInflater.from(parent.context).inflate(
            R.layout.prev_match_list, parent, false
        )
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(matches[position])
    }

    override fun getItemCount(): Int = matches.size

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bindItem(match: Match) {
            val date = dateFormatter(match.matchDate)
            val time = timeFormatter(match.matchTime)

            itemView.tvHomeTeamPrev.text = match.homeTeamName
            itemView.tvAwayTeamPrev.text = match.awayTeamName
            itemView.tvDateTimePrev.text = toGmtFormat("$date $time")
            itemView.tvHomeScorePrev.text = match.homeScore
            itemView.tvAwayScorePrev.text = match.awayScore
            itemView.setOnClickListener { listener(match) }
        }
    }

}


package com.dicoding.millatip.footballapps.presentation.ui.favoritematches

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.dicoding.millatip.footballapps.R
import com.dicoding.millatip.footballapps.data.model.FavoriteMatch
import com.dicoding.millatip.footballapps.utils.dateFormatter
import com.dicoding.millatip.footballapps.utils.timeFormatter
import com.dicoding.millatip.footballapps.utils.toGmtFormat
import kotlinx.android.synthetic.main.prev_match_list.view.*

class FavoriteMatchAdapter(private val matches: List<FavoriteMatch>, val listener: (FavoriteMatch) -> Unit) :  RecyclerView.Adapter<FavoriteMatchAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(
        LayoutInflater.from(parent.context).inflate(
            R.layout.prev_match_list, parent, false
        )
    )

    override fun getItemCount(): Int = matches.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(matches[position])
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindItem(match: FavoriteMatch) {
            val date = dateFormatter(match.matchDate)
            val time = timeFormatter(match.matchTime)

            itemView.tvHomeTeamPrev.text = match.homeTeam
            itemView.tvAwayTeamPrev.text = match.awayTeam
            itemView.tvHomeScorePrev.text = match.homeScore
            itemView.tvAwayScorePrev.text = match.awayScore
            itemView.tvDateTimePrev.text = toGmtFormat("$date $time")
            itemView.setOnClickListener { listener(match)}
        }

    }
}

package com.dicoding.millatip.footballapps.presentation.ui.nextmatch

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.dicoding.millatip.footballapps.R
import com.dicoding.millatip.footballapps.data.model.Match
import com.dicoding.millatip.footballapps.utils.dateFormatter
import com.dicoding.millatip.footballapps.utils.timeFormatter
import com.dicoding.millatip.footballapps.utils.toGmtFormat
import kotlinx.android.synthetic.main.next_match_list.view.*

class NextMatchAdapter(private val matches: List<Match>, val notificationListener: (Match) -> Unit) : RecyclerView.Adapter<NextMatchAdapter.ViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): NextMatchAdapter.ViewHolder = ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.next_match_list, parent, false))

    override fun getItemCount(): Int = matches.size

    override fun onBindViewHolder(holder: NextMatchAdapter.ViewHolder, position: Int) {
        holder.bindItem(matches[position])
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

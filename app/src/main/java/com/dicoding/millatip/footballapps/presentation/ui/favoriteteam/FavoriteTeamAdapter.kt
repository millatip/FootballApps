package com.dicoding.millatip.footballapps.presentation.ui.favoriteteam

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.dicoding.millatip.footballapps.R
import com.dicoding.millatip.footballapps.data.model.FavoriteTeam
import kotlinx.android.synthetic.main.item_team_list.view.*

class FavoriteTeamAdapter(
    val context: Context,
    private val teams: List<FavoriteTeam>,
    val listener: (FavoriteTeam) -> Unit
) :
    RecyclerView.Adapter<FavoriteTeamAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(
        LayoutInflater.from(parent.context).inflate(
            R.layout.item_team_list, parent, false
        )
    )

    override fun getItemCount(): Int = teams.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(teams[position], context)
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindItem(team: FavoriteTeam, context: Context) {
            Glide.with(context).load(team.teamBadge).into(itemView.ivBadge)
            itemView.tvTeamName.text = team.teamName
            itemView.setOnClickListener { listener(team) }
        }

    }
}

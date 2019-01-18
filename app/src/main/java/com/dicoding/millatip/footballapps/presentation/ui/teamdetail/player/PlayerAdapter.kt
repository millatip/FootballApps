package com.dicoding.millatip.footballapps.presentation.ui.teamdetail.player

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.dicoding.millatip.footballapps.R
import com.dicoding.millatip.footballapps.data.model.Player
import kotlinx.android.synthetic.main.item_player_list.view.*

class PlayerAdapter(val context: Context?, private val players: List<Player>) :
    RecyclerView.Adapter<PlayerAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(LayoutInflater.from(parent.context).inflate(
        R.layout.item_player_list, parent, false))

    override fun getItemCount(): Int = players.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (context != null) {
            holder.bindItem(context, players[position])
        }
    }

    class ViewHolder(itemView: View) :RecyclerView.ViewHolder(itemView) {
        fun bindItem(context: Context, player: Player){
            Glide.with(context).load(player.imageCutout).into(itemView.ivPicture)
            itemView.tvPlayerName.text = player.playerName
            itemView.tvPlayerPosition.text = player.position
        }
    }
}

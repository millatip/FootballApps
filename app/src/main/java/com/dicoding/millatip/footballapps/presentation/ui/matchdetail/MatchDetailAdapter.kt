package com.dicoding.millatip.footballapps.presentation.ui.matchdetail

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.dicoding.millatip.footballapps.R
import kotlinx.android.synthetic.main.item_home_list.view.*

class MatchDetailAdapter(private val items: List<String>?, private val type: String) :
    RecyclerView.Adapter<MatchDetailAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return when (viewType) {
            1 -> ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_home_list, parent, false))
            else -> ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_away_list, parent, false))
        }
    }

    override fun getItemCount(): Int = items?.size ?: 0

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(items?.get(position))
    }

    override fun getItemViewType(position: Int): Int {
        return when (type) {
            "home" -> 1
            else -> 0
        }
    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindItem(item: String?) {
            itemView.tvItem.text = item?.trim()
        }
    }

}

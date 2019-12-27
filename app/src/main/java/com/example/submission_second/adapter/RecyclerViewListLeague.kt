package com.example.submission_second.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.submission_second.databinding.ListClubBinding
import com.example.submission_second.model.model.league_list.LeagueData

class RecyclerViewListLeague(var leagueList: List<LeagueData>, var listener: OnListLeaguePressedListener) :
    RecyclerView.Adapter<RecyclerViewListLeague.ViewHolder>() {

    class ViewHolder(val binding: ListClubBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(model: LeagueData, listener: OnListLeaguePressedListener, position: Int) {
            binding.model = model
            binding.executePendingBindings()
            binding.root.setOnClickListener {
                listener.onListLeaguePressed(model, position)
            }
        }
    }

    interface OnListLeaguePressedListener {
        fun onListLeaguePressed(leagueList: LeagueData, position: Int)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ListClubBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = leagueList.size
    fun refreshData(leagueList: List<LeagueData>) {
        this.leagueList = leagueList
        notifyDataSetChanged()
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(leagueList[position], listener, position)
}



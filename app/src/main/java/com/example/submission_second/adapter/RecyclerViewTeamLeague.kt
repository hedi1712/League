package com.example.submission_second.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.submission_second.databinding.ListTeamBinding
import com.example.submission_second.model.model.list_of_competition_team.Team

class RecyclerViewTeamLeague(var model: List<Team>, var listener: OnclickTeam) :
    RecyclerView.Adapter<RecyclerViewTeamLeague.ViewHolder>() {

    class ViewHolder(val binding: ListTeamBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(model: Team, position: Int, listener: OnclickTeam) {
            binding.model = model
            binding.executePendingBindings()
            binding.root.setOnClickListener {
                listener.onPressed(model, position)
            }
        }

    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ListTeamBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = model.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(model[position], position, listener)
    }

    interface OnclickTeam {
        fun onPressed(model: Team, position: Int)
    }

    fun refreshData(model: List<Team>) {
        this.model = model
        notifyDataSetChanged()
    }

}
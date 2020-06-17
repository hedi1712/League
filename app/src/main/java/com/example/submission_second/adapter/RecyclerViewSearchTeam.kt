package com.example.submission_second.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.submission_second.databinding.ListSearchTeamBinding
import com.example.submission_second.model.model.search_team.Team

class RecyclerViewSearchTeam(var model: List<Team>, var listener: OnClickTeam) :
    RecyclerView.Adapter<RecyclerViewSearchTeam.ViewHolder>() {

    class ViewHolder(val binding: ListSearchTeamBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(model: Team, position: Int, listener: OnClickTeam) {
            binding.model = model
            binding.executePendingBindings()
            binding.root.setOnClickListener {
                listener.OnPressed(model, position)
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ListSearchTeamBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = model.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(model[position], position, listener)
    }

    fun refreshData(model: List<Team>) {
        this.model = model
        notifyDataSetChanged()
    }

    interface OnClickTeam {
        fun OnPressed(model: Team, position: Int)
    }
}
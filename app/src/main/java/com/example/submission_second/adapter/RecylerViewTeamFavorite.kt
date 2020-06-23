package com.example.submission_second.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.submission_second.databinding.ListFavoriteTeamBinding
import com.example.submission_second.db.entity.EntityFavorite
import com.example.submission_second.db.entity.EntityTeam

class RecylerViewTeamFavorite(var team: List<EntityTeam>, var onclick: OnclickDelete) :
    RecyclerView.Adapter<RecylerViewTeamFavorite.ViewHolder>() {

    class ViewHolder(val binding: ListFavoriteTeamBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(model: EntityTeam, onclick: OnclickDelete, position: Int) {
            binding.model = model
            binding.executePendingBindings()
            binding.root.setOnClickListener {
                onclick.onDetail(model, position)
            }
            binding.remove.setOnClickListener {
                onclick.onDelete(model)
            }
        }

    }

    interface OnclickDelete {
        fun onDetail(model: EntityTeam, position: Int)
        fun onDelete(model: EntityTeam)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ListFavoriteTeamBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = team.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(team[position], onclick, position)
    }

    fun refreshData(teamFavorite: List<EntityTeam>) {
        this.team = teamFavorite
        notifyDataSetChanged()
    }

}
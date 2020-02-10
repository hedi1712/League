package com.example.submission_second.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.submission_second.databinding.ListDetailMatchBinding
import com.example.submission_second.model.model.detail_match.DetailMatchResponse

class RecyclerViewDetailMatch(var model: List<DetailMatchResponse.DetailMatchData>) :
    RecyclerView.Adapter<RecyclerViewDetailMatch.ViewHolder>() {
    class ViewHolder(val binding: ListDetailMatchBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(model: DetailMatchResponse.DetailMatchData, position: Int) {
            binding.model = model
            binding.executePendingBindings()
        }
    }
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ListDetailMatchBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = model.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.bind(model[position], position)
}
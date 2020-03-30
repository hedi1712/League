package com.example.submission_second.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.submission_second.databinding.SearchListBinding
import com.example.submission_second.model.model.search_match.SearchMatchData

class RecyclerSearch(private var searchData: List<SearchMatchData>, var listener: Onclick) :
    RecyclerView.Adapter<RecyclerSearch.ViewHolder>() {
    class ViewHolder(val binding: SearchListBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(model: SearchMatchData, listener: Onclick, position: Int) {
            binding.model = model
            binding.executePendingBindings()
            binding.favorite.setOnClickListener {
                listener.favoriteToDatabase(model)
            }
            binding.root.setOnClickListener {
                listener.onClick(model, position)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = SearchListBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = searchData.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.bind(searchData[position], listener, position)


    fun refreshData(searchData: List<SearchMatchData>) {
        this.searchData = searchData
        notifyDataSetChanged()
    }

    interface Onclick {
        fun onClick(searchData: SearchMatchData, position: Int)
        fun favoriteToDatabase(searchData: SearchMatchData)
    }
}


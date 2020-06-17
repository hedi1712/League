package com.example.submission_second.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.submission_second.databinding.ListNextMatchBinding
import com.example.submission_second.model.model.next_match.Event

class RecyclerViewNextmatchAdapter(var nextmatch: List<Event>, var listerner: OnNextMatchPressed) : RecyclerView.Adapter<RecyclerViewNextmatchAdapter.ViewHolder>()
{
    class ViewHolder(val binding: ListNextMatchBinding) : RecyclerView.ViewHolder(binding.root)
    {
        fun bind(model: Event, position: Int, listerner: OnNextMatchPressed) {
            binding.model = model
            binding.executePendingBindings()
            binding.favorite.setOnClickListener { listerner.favoriteTeam(model) }
            binding.root.setOnClickListener {
                listerner.onPressed(model, position)
            }
        }
    }

    interface OnNextMatchPressed {
        fun onPressed(model: Event, position: Int)
        fun favoriteTeam(model: Event)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ListNextMatchBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = nextmatch.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(nextmatch[position], position, listerner)
    }

    fun refreshData(nextmatch: List<Event>) {
        this.nextmatch = nextmatch
        notifyDataSetChanged()
    }
}
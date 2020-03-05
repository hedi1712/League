package com.example.submission_second.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.submission_second.databinding.ListPreviousMatchBinding
import com.example.submission_second.model.model.previous_match.PreviousMatchData
import com.example.submission_second.model.model.previous_match.PreviousMatchResponse
import com.example.submission_second.ui.previous_match.PreviousMatch
import com.example.submission_second.util.toddMMyyyy

class RecyclerViewPreviousMatchAdapter(
    var previousMatch: List<PreviousMatchData>,
    val listener: OnPreviousMatch
) :
    RecyclerView.Adapter<RecyclerViewPreviousMatchAdapter.ViewHolder>() {
    class ViewHolder(val binding: ListPreviousMatchBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(model: PreviousMatchData, position: Int, listener: OnPreviousMatch
        ) {
            binding.model = model
            binding.executePendingBindings()
            binding.root.setOnClickListener {
                listener.onPressed(model, position)

            }
        }
    }

    interface OnPreviousMatch {
        fun onPressed(model: PreviousMatchData, position: Int)

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ListPreviousMatchBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = previousMatch.size
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(previousMatch[position], position, listener)
    }

    fun refreshData(previousMatch: List<PreviousMatchData>) {
        this.previousMatch = previousMatch
        notifyDataSetChanged()
    }

}
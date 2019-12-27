package com.example.submission_second.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.submission_second.databinding.FragmentDetailMatchBinding
import com.example.submission_second.databinding.FragmentPreviousMatchBinding
import com.example.submission_second.model.model.previous_match.PreviousMatchResponse
import com.example.submission_second.ui.previous_match.PreviousMatch

class RecyclerViewPreviousMatchAdapter(
    var previousMatch: List<PreviousMatchResponse.PreviousMatchData>,
    val listener: OnPreviousMatch
) :
    RecyclerView.Adapter<RecyclerViewPreviousMatchAdapter.ViewHolder>() {
    class ViewHolder(val binding: FragmentPreviousMatchBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(
            model: PreviousMatchResponse.PreviousMatchData,
            position: Int,
            listener: OnPreviousMatch
        ) {
            binding.model = model
            binding.executePendingBindings()
            binding.root.setOnClickListener {
                listener.onPressed(model, position)

            }
        }
    }

    interface OnPreviousMatch {
        fun onPressed(model: PreviousMatchResponse.PreviousMatchData, position: Int)

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = FragmentPreviousMatchBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = previousMatch.size
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(previousMatch[position], position,listener)
    }

    fun refreshData(previousMatch: List<PreviousMatchResponse.PreviousMatchData>) {
        this.previousMatch = previousMatch
        notifyDataSetChanged()
    }

}
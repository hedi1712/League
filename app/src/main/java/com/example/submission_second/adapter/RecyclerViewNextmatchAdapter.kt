package com.example.submission_second.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.submission_second.databinding.FragmentDetailMatchBinding
import com.example.submission_second.model.model.next_match.NextMatchResponse

class RecyclerViewNextmatchAdapter(
    var nextmatch: List<NextMatchResponse.Event>,
    var listerner: OnNextMatchPressed
) :
    RecyclerView.Adapter<RecyclerViewNextmatchAdapter.ViewHolder>() {

    class ViewHolder(val binding: FragmentDetailMatchBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(model: NextMatchResponse.Event, position: Int, listerner: OnNextMatchPressed) {
            binding.model = model
            binding.executePendingBindings()
            binding.root.setOnClickListener {
                listerner.onPressed(model, position)

            }
        }

    }

    interface OnNextMatchPressed {
        fun onPressed(model: NextMatchResponse.Event, position: Int)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = FragmentDetailMatchBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = nextmatch.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(nextmatch[position], position, listerner)
    }

    fun refreshData(nextmatch: List<NextMatchResponse.Event>) {
        this.nextmatch = nextmatch
        notifyDataSetChanged()

    }
}
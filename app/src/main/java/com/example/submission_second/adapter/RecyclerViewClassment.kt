package com.example.submission_second.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.submission_second.databinding.ListClassmentBinding
import com.example.submission_second.model.model.classment.Table

class RecyclerViewClassment(var classment: List<Table>) :
    RecyclerView.Adapter<RecyclerViewClassment.ViewHolder>() {

    class ViewHolder(val binding: ListClassmentBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(model: Table) {
            binding.model = model
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ListClassmentBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = classment.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(classment[position])
    }

    fun refreshDataClassment(modelClassment: List<Table>) {
        this.classment = modelClassment
        notifyDataSetChanged()
    }
}
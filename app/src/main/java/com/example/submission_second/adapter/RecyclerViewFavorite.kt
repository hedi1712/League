package com.example.submission_second.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.submission_second.databinding.FavoriteListBinding
import com.example.submission_second.db.entity.EntityFavorite

class RecyclerViewFavorite(var model: List<EntityFavorite>, var listener: OnItemPressed) :
    RecyclerView.Adapter<RecyclerViewFavorite.ViewHolder>() {


    class ViewHolder(val binding: FavoriteListBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(model: EntityFavorite, listener: OnItemPressed, position: Int) {
            binding.model = model
            binding.executePendingBindings()
            binding.delete.setOnClickListener {
                listener.deleteData(model)
            }
            binding.root.setOnClickListener {
                listener.onItemClick(model, position)
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = FavoriteListBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = model.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.bind(model[position], listener, position)

    fun refreshData(searchData: List<EntityFavorite>) {
        this.model = searchData
        notifyDataSetChanged()
    }

    interface OnItemPressed {
        fun onItemClick(model: EntityFavorite, position: Int)
        fun deleteData(model: EntityFavorite)
    }
}
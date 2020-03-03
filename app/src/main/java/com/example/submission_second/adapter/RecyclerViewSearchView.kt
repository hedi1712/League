package com.example.submission_second.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.submission_second.databinding.ListSearchViewBinding
import com.example.submission_second.model.model.search_match.SearchMatchResponse

class RecyclerViewSearchView(
    var listSearch: List<SearchMatchResponse.SearchData>,
    var listener: OnClick
) :
    RecyclerView.Adapter<RecyclerViewSearchView.ViewHolder>() {
    class ViewHolder(val binding: ListSearchViewBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(modelBind: SearchMatchResponse.SearchData, position: Int, listener: OnClick) {
            binding.model = modelBind
            binding.root.setOnClickListener {
                listener.onClickListener(modelBind, position)
            }
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ListSearchViewBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = listSearch.size

    override fun onBindViewHolder(holder: RecyclerViewSearchView.ViewHolder, position: Int) =
        holder.bind(listSearch[position], position, listener)

    fun refreshData(listSearch: List<SearchMatchResponse.SearchData>) {
        this.listSearch = listSearch
        notifyDataSetChanged()
    }

    interface OnClick {
        fun onClickListener(model: SearchMatchResponse.SearchData, position: Int)
    }

}


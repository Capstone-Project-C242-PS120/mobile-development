package com.example.capstone.ui.explore

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.capstone.R
import com.example.capstone.data.remote.response.DataItem
import com.example.capstone.databinding.SearchCardBinding

class SearchAdapter(private val onItemClick: (DataItem) -> Unit): ListAdapter<DataItem, SearchAdapter.SearchViewHolder>(DIFF_CALLBACK) {

    inner class SearchViewHolder(private val binding: SearchCardBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(item: DataItem) {
            with(binding) {
                searchFoodName.text = item.name
                searchType.text = item.type
                searchGrade.text = item.grade

                val imageUrl = item.imageUrl
                Glide.with(root.context)
                    .load(if(imageUrl.isNullOrEmpty()) R.drawable.image_default else imageUrl)
                    .into(imgSearch)

                root.setOnClickListener {
                    onItemClick(item)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        val binding = SearchCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SearchViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        val item = getItem(position)
        if (item != null) {
            holder.bind(item)
        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<DataItem>() {
            override fun areItemsTheSame(oldItem: DataItem, newItem: DataItem): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: DataItem, newItem: DataItem): Boolean {
                return oldItem == newItem
            }

        }
    }
}
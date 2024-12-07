package com.example.capstone.ui.dashboard

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.capstone.R
import com.example.capstone.data.remote.response.DataItemRecomendation
import com.example.capstone.databinding.HistoryCardBinding

class RecomendationAdapter(private val onItemClick: (DataItemRecomendation) -> Unit) :
    ListAdapter<DataItemRecomendation, RecomendationAdapter.RecomendationViewHolder>(DIFF_CALLBACK) {

    inner class RecomendationViewHolder(private val binding: HistoryCardBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: DataItemRecomendation) {
            with(binding) {
                textViewTitle.text = item.name
                textViewCategory.text = item.tags.joinToString(", ")
                textViewRank.text = item.grade.toString()

                Glide.with(root.context)
                    .load(if (item.imageUrl.isNullOrEmpty()) R.drawable.image_default else item.imageUrl)
                    .into(imageViewIcon)

                root.setOnClickListener {
                    onItemClick(item)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecomendationViewHolder {
        val binding = HistoryCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RecomendationViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecomendationViewHolder, position: Int) {
        val item = getItem(position)
        if (item != null) {
            holder.bind(item)
        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<DataItemRecomendation>() {
            override fun areItemsTheSame(oldItem: DataItemRecomendation, newItem: DataItemRecomendation): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: DataItemRecomendation, newItem: DataItemRecomendation): Boolean {
                return oldItem == newItem
            }
        }
    }
}

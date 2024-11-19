package com.example.capstone.ui.history

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.capstone.R

class HistoryAdapter (private val listHistory: ArrayList<History>) : RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder>() {

    class HistoryViewHolder(itemView: View) :RecyclerView.ViewHolder(itemView) {
        val imgPhoto : ImageView =itemView.findViewById(R.id.imageViewIcon)
        val tvName : TextView = itemView.findViewById(R.id.textViewTitle)
        val tvcategory : TextView =itemView.findViewById(R.id.textViewCategory)
        val tvrank : TextView =itemView.findViewById(R.id.textViewRank)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        val view : View = LayoutInflater.from(parent.context).inflate(R.layout.history_card,parent,false)
        return HistoryViewHolder(view)
    }

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        val (name, category, photo, rank) = listHistory[position]
        Glide.with(holder.itemView.context)
            .load(photo)
            .into(holder.imgPhoto)
        holder.tvName.text = name
        holder.tvcategory.text = category
        holder.tvrank.text = rank



    }

    override fun getItemCount(): Int =listHistory.size

}
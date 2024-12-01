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
import com.example.capstone.ui.fooddetail.FoodDetailActivity

class HistoryAdapter(private val listHistory: ArrayList<History>) : RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder>() {

    class HistoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imgPhoto: ImageView = itemView.findViewById(R.id.imageViewIcon)
        val tvName: TextView = itemView.findViewById(R.id.textViewTitle)
        val tvcategory: TextView = itemView.findViewById(R.id.textViewCategory)
        val tvrank: TextView = itemView.findViewById(R.id.textViewRank)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.history_card, parent, false)
        return HistoryViewHolder(view)
    }

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        val history = listHistory[position]

        // Load image into ImageView using Glide
        Glide.with(holder.itemView.context)
            .load(history.photo)
            .into(holder.imgPhoto)

        // Set data to TextViews
        holder.tvName.text = history.name
        holder.tvcategory.text = history.category
        holder.tvrank.text = history.rank

        // Set click listener
        holder.itemView.setOnClickListener {
            val intent = Intent(holder.itemView.context, FoodDetailActivity::class.java)
            intent.putExtra("HISTORY_DETAIL", history)  // Pass the History object
            holder.itemView.context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int = listHistory.size
}

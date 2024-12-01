package com.example.capstone.ui.history

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.capstone.R

class HistoryFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private val list = ArrayList<History>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_history, container, false)

        recyclerView = view.findViewById(R.id.recyclerViewHistory)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        val foodNames = resources.getStringArray(R.array.data_name)
        val foodCategories = resources.getStringArray(R.array.data_food_category)
        val foodRanks = resources.getStringArray(R.array.data_food_rank)
        val foodImages = resources.getStringArray(R.array.data_food_photo)

        for (i in foodNames.indices) {
            val history = History(
                name = foodNames[i],
                category = foodCategories[i],
                photo = foodImages[i],
                rank = foodRanks[i]
            )
            list.add(history)
        }


        val adapter = HistoryAdapter(list)
        recyclerView.adapter = adapter

        return view
    }
}

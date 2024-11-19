package com.example.capstone.ui.dashboard

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.capstone.R
import com.example.capstone.ui.history.History
import com.example.capstone.ui.history.HistoryAdapter

class FragmentDashboard : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private val recentHistoryList = ArrayList<History>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_dashboard, container, false)

        // Initialize RecyclerView
        recyclerView = view.findViewById(R.id.recyleviewdashboard)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        // Load recent history data (example data or real data source)
        loadRecentHistory()

        // Set up the adapter with the data
        val adapter = HistoryAdapter(recentHistoryList)
        recyclerView.adapter = adapter

        return view
    }

    private fun loadRecentHistory() {
        // Example data
        val recentHistoryNames = resources.getStringArray(R.array.data_name)
        val recentHistoryCategories = resources.getStringArray(R.array.data_food_category)
        val recentHistoryRanks = resources.getStringArray(R.array.data_food_rank)
        val recentHistoryPhotos = resources.getStringArray(R.array.data_food_photo)

        // Populate the list with data
        for (i in recentHistoryNames.indices) {
            val history = History(
                name = recentHistoryNames[i],
                category = recentHistoryCategories[i],
                photo = recentHistoryPhotos[i],
                rank = recentHistoryRanks[i]
            )
            recentHistoryList.add(history)
        }
    }
}

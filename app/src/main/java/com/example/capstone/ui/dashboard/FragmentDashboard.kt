package com.example.capstone.ui.dashboard

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.capstone.R
import com.example.capstone.data.Result
import com.example.capstone.databinding.FragmentDashboardBinding
import com.example.capstone.pref.SessionManager
import com.example.capstone.ui.factory.ViewModelFactory
import com.example.capstone.ui.fooddetail.FoodDetailActivity
import com.example.capstone.ui.news.NewsActivity

class FragmentDashboard : Fragment() {
    private var _binding: FragmentDashboardBinding? = null
    private val binding get() = _binding!!
    private lateinit var sessionManager: SessionManager
    private val viewModel: DashboardViewModel by viewModels {
        ViewModelFactory.getInstance(requireContext())
    }
    private lateinit var recomendationAdapter: RecomendationAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sessionManager = SessionManager(requireContext())
        val token = sessionManager.getAuthToken().toString()
        viewModel.getSummary(token)
        viewModel.getNews(token)

        recomendationAdapter = RecomendationAdapter { selectedItem ->
            val intent = Intent(requireContext(), FoodDetailActivity::class.java).apply {
                putExtra("ITEM_ID", selectedItem.id)
            }
            requireContext().startActivity(intent)
        }
        binding.rcRecomendation.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = recomendationAdapter
        }
        observeViewModel()
    }

    private fun observeViewModel() {
        viewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            showLoading(isLoading)
        }
        viewModel.summaryResult.observe(viewLifecycleOwner) { result ->
            when(result) {
                is Result.Loading -> showLoading(true)
                is Result.Error -> Toast.makeText(requireContext(), result.error, Toast.LENGTH_SHORT).show()
                is Result.Success -> {
                    val summary = result.data.data
                    binding.txtUsername.text = summary.name
                    binding.txtKalori.text = "${summary.calories}kcal"
                    binding.txtProtein.text = "${summary.protein}g"
                    binding.txtGula.text = "${summary.sugar}g"
                }
            }
        }
        viewModel.newsResult.observe(viewLifecycleOwner) { result ->
            when(result) {
                is Result.Loading -> showLoading(true)
                is Result.Error -> Toast.makeText(requireContext(), result.error, Toast.LENGTH_SHORT).show()
                is Result.Success -> {
                    val news = result.data.data

                    binding.includeNewsCard.newsName.text = news.title
                    binding.includeNewsCard.newsLitledesc.text = news.description
                    binding.includeNewsCard.newsButtonUrl.setOnClickListener {
                        val intent = Intent(requireContext(), NewsActivity::class.java)
                        intent.putExtra("NEWS_URL", news.url)
                        startActivity(intent)
                    }
                    Glide.with(this)
                        .load(news.imageUrl)
                        .into(binding.includeNewsCard.imgNews)
                }
            }
        }
        viewModel.recomendationResult.observe(viewLifecycleOwner) { result ->
            when(result) {
                is Result.Loading -> showLoading(true)
                is Result.Error -> Toast.makeText(requireContext(), result.error, Toast.LENGTH_SHORT).show()
                is Result.Success -> {
                    val recomendation = result.data.data
                    if (recomendation.isEmpty()) {
                        Toast.makeText(requireContext(), "Tidak ada ditemukan", Toast.LENGTH_SHORT).show()
                    } else {
                        recomendationAdapter.submitList(recomendation)
                    }
                }
            }
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}

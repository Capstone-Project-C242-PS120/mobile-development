package com.example.capstone.ui.fooddetail

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import com.example.capstone.R
import com.example.capstone.data.Result
import com.example.capstone.data.remote.response.DetailFood
import com.example.capstone.databinding.ActivityFoodDetailBinding
import com.example.capstone.pref.SessionManager
import com.example.capstone.ui.factory.ViewModelFactory

class FoodDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFoodDetailBinding
    private lateinit var sessionManager: SessionManager
    private val viewModel: FoodDetailViewModel by viewModels {
        ViewModelFactory.getInstance(this)
    }
    private var foodRate = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityFoodDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        sessionManager = SessionManager(this)
        val token = sessionManager.getAuthToken().toString()
        val foodId = intent.getIntExtra("ITEM_ID", -1)
        if (foodId == -1) {
            finish()
            return
        }

        getFoodRate()
        viewModel.detailFood(token, foodId)

        observeViewModel()
        binding.close.setOnClickListener {
            finish()
        }
        binding.btnSave.setOnClickListener {
            viewModel.saveFood(token, foodId, foodRate)
        }


    }

    private fun getFoodRate() {
        val rateImages = listOf(
            binding.imgRate1,
            binding.imgRate2,
            binding.imgRate3,
            binding.imgRate4,
            binding.imgRate5
        )


        val coloredImages = listOf(
            R.drawable.ic_rate_4,
            R.drawable.ic_rate_4,
            R.drawable.ic_rate_4,
            R.drawable.ic_rate_4,
            R.drawable.ic_rate_4,
        )

        val grayImages = listOf(
            R.drawable.ic_bw_rate_3,
            R.drawable.ic_bw_rate_3,
            R.drawable.ic_bw_rate_3,
            R.drawable.ic_bw_rate_3,
            R.drawable.ic_bw_rate_3,
        )

        rateImages.forEachIndexed { index, imageView ->
            imageView.setOnClickListener {
                foodRate = index + 1

                rateImages.forEachIndexed { i, img ->
                    img.setImageResource(grayImages[i])
                }

                imageView.setImageResource(coloredImages[index])
                animateZoom(imageView)
            }
        }
    }


    private fun animateZoom(view: View) {
        val scaleUpX = ObjectAnimator.ofFloat(view, "scaleX", 1.2f).apply {
            duration = 150
        }
        val scaleUpY = ObjectAnimator.ofFloat(view, "scaleY", 1.2f).apply {
            duration = 150
        }
        val scaleDownX = ObjectAnimator.ofFloat(view, "scaleX", 1f).apply {
            duration = 150
        }
        val scaleDownY = ObjectAnimator.ofFloat(view, "scaleY", 1f).apply {
            duration = 150
        }

        AnimatorSet().apply {
            play(scaleUpX).with(scaleUpY)
            play(scaleDownX).with(scaleDownY).after(scaleUpX)
            start()
        }
    }

    private fun observeViewModel() {
        viewModel.isLoading.observe(this) { isLoading ->
            showLoading(isLoading)
        }
        viewModel.detailResult.observe(this) { result ->
            when(result) {
                is Result.Loading -> showLoading(true)
                is Result.Error -> Toast.makeText(this, result.error, Toast.LENGTH_SHORT).show()
                is Result.Success -> {
                    val foodDetail = result.data.data
                    setupView(foodDetail)
                }
            }
        }
        viewModel.saveResult.observe(this) { result ->
            when(result) {
                is Result.Loading -> showLoading(true)
                is Result.Error -> Toast.makeText(this, result.error, Toast.LENGTH_SHORT).show()
                is Result.Success -> {
                    Toast.makeText(this, result.data.message, Toast.LENGTH_SHORT).show()
                    finish()
                }
            }
        }
    }

    private fun setupView(foodDetail: DetailFood) {
        binding.apply {
            txtFoodName.text = foodDetail.name
            txtCalories.text = "${foodDetail.calories} kcal"
            txtProtein.text = "${foodDetail.protein} g"
            txtSugar.text = "${foodDetail.sugar} g"
            txtFat.text = "${foodDetail.fat} g"
            txtFiber.text = "${foodDetail.fiber} g"
            txtNatrium.text = "${foodDetail.natrium} mg"

            txtTags.text = foodDetail.tags.joinToString(", ")

            val imageUrl = foodDetail.imageUrl
            Glide.with(this@FoodDetailActivity)
                .load(if (imageUrl.isNullOrEmpty()) R.drawable.image_default else imageUrl)
                .into(imgPreview)

            when(foodDetail.grade) {
                'A' -> {
                    rankA.background = ContextCompat.getDrawable(this@FoodDetailActivity, R.drawable.food_rank_a)
                    rankA.setTextColor(ContextCompat.getColor(this@FoodDetailActivity, android.R.color.white))
                    binding.txtKeterangan.text = getString(R.string.keterangan_A)
                }
                'B' -> {
                    rankB.background = ContextCompat.getDrawable(this@FoodDetailActivity, R.drawable.food_rank_b)
                    rankB.setTextColor(ContextCompat.getColor(this@FoodDetailActivity, android.R.color.white))
                    binding.txtKeterangan.text = getString(R.string.keterangan_B)
                }
                'C' -> {
                    rankC.background = ContextCompat.getDrawable(this@FoodDetailActivity, R.drawable.food_rank_c)
                    rankC.setTextColor(ContextCompat.getColor(this@FoodDetailActivity, android.R.color.white))
                    binding.txtKeterangan.text = getString(R.string.keterangan_C)
                }
                'D' -> {
                    rankD.background = ContextCompat.getDrawable(this@FoodDetailActivity, R.drawable.food_rank_d)
                    rankD.setTextColor(ContextCompat.getColor(this@FoodDetailActivity, android.R.color.white))
                    binding.txtKeterangan.text = getString(R.string.keterangan_D)
                }
                'E' -> {
                    rankE.background = ContextCompat.getDrawable(this@FoodDetailActivity, R.drawable.food_rank_e)
                    rankE.setTextColor(ContextCompat.getColor(this@FoodDetailActivity, android.R.color.black))
                    binding.txtKeterangan.text = getString(R.string.keterangan_E)
                }
            }
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

}
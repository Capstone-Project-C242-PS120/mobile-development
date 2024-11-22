package com.example.capstone.ui.fooddetail

import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.capstone.R
import com.example.capstone.data.remote.response.AnalyzeData
import com.example.capstone.databinding.ActivityFoodDetailBinding

class FoodDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFoodDetailBinding
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

        val imageUriString = intent.getStringExtra("IMAGE_URI")

        val analyzeData: AnalyzeData? = intent.getParcelableExtra("ANALYZE_DATA")

        setupView(imageUriString, analyzeData)

    }

    private fun setupView(imageUriString: String?, analyzeData: AnalyzeData?) {
        if (imageUriString != null) {
            val imageUri = Uri.parse(imageUriString)
            binding.imgPreview.setImageURI(imageUri)
        }

        analyzeData?.let {
            binding.apply {
                txtCalories.text = it.calories.toString()
                txtProtein.text = it.protein.toString()
                txtSugar.text = it.sugar.toString()
                txtFat.text = it.fat.toString()
                txtCarbo.text = it.carbo.toString()
                txtVitaminc.text = it.vitaminc.toString()

                when(it.rank) {
                    "A" -> {
                        rankA.background = ContextCompat.getDrawable(this@FoodDetailActivity, R.drawable.food_rank_a)
                        rankA.setTextColor(ContextCompat.getColor(this@FoodDetailActivity, android.R.color.white))
                    }
                    "B" -> {
                        rankB.background = ContextCompat.getDrawable(this@FoodDetailActivity, R.drawable.food_rank_b)
                        rankB.setTextColor(ContextCompat.getColor(this@FoodDetailActivity, android.R.color.white))
                    }
                    "C" -> {
                        rankC.background = ContextCompat.getDrawable(this@FoodDetailActivity, R.drawable.food_rank_c)
                        rankC.setTextColor(ContextCompat.getColor(this@FoodDetailActivity, android.R.color.white))
                    }
                    "D" -> {
                        rankD.background = ContextCompat.getDrawable(this@FoodDetailActivity, R.drawable.food_rank_d)
                        rankD.setTextColor(ContextCompat.getColor(this@FoodDetailActivity, android.R.color.white))
                    }
                    "E" -> {
                        rankE.background = ContextCompat.getDrawable(this@FoodDetailActivity, R.drawable.food_rank_e)
                        rankE.setTextColor(ContextCompat.getColor(this@FoodDetailActivity, android.R.color.black))
                    }
                }

            }

        }
    }
}
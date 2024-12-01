package com.example.capstone.ui.fooddetail

import android.net.Uri
import android.os.Bundle
import android.widget.CheckBox
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.capstone.R
import com.example.capstone.data.remote.response.DataAnalyze
import com.example.capstone.databinding.ActivityScanFoodDetailBinding

class ScanFoodDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityScanFoodDetailBinding
    private val selectedCategories = mutableListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityScanFoodDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val selectedCategoriesTextView = findViewById<TextView>(R.id.selectedCategories)
        selectedCategoriesTextView.setOnClickListener {
            showMultiSelectDialog()
        }

        val imageUriString = intent.getStringExtra("IMAGE_URI")

        val analyzeData: DataAnalyze? = intent.getParcelableExtra("ANALYZE_DATA")

        setupView(imageUriString, analyzeData)

        binding.close.setOnClickListener {
            finish()
        }

    }

    private fun showMultiSelectDialog() {
        val categories = resources.getStringArray(R.array.categories)
        val selectedItems = BooleanArray(categories.size)

        val layout = LinearLayout(this)
        layout.orientation = LinearLayout.VERTICAL
        for (i in categories.indices) {
            val checkBox = CheckBox(this)
            checkBox.text = categories[i]
            checkBox.isChecked = selectedItems[i]
            layout.addView(checkBox)
        }

        AlertDialog.Builder(this)
            .setTitle("Select Categories")
            .setView(layout)
            .setPositiveButton("OK") { _, _ ->
                selectedCategories.clear()
                for (i in categories.indices) {
                    val checkBox = layout.getChildAt(i) as CheckBox
                    if (checkBox.isChecked) {
                        selectedCategories.add(categories[i])
                    }
                }


                val selectedText = selectedCategories.joinToString(", ")
                findViewById<TextView>(R.id.selectedCategories).text = selectedText
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    private fun setupView(imageUriString: String?, analyzeData: DataAnalyze?) {
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
                txtFiber.text = it.fiber.toString()
                txtNatrium.text = it.natrium.toString()

                when(it.grade) {
                    'A' -> {
                        rankA.background = ContextCompat.getDrawable(this@ScanFoodDetailActivity, R.drawable.food_rank_a)
                        rankA.setTextColor(ContextCompat.getColor(this@ScanFoodDetailActivity, android.R.color.white))
                    }
                    'B' -> {
                        rankB.background = ContextCompat.getDrawable(this@ScanFoodDetailActivity, R.drawable.food_rank_b)
                        rankB.setTextColor(ContextCompat.getColor(this@ScanFoodDetailActivity, android.R.color.white))
                    }
                    'C' -> {
                        rankC.background = ContextCompat.getDrawable(this@ScanFoodDetailActivity, R.drawable.food_rank_c)
                        rankC.setTextColor(ContextCompat.getColor(this@ScanFoodDetailActivity, android.R.color.white))
                    }
                    'D' -> {
                        rankD.background = ContextCompat.getDrawable(this@ScanFoodDetailActivity, R.drawable.food_rank_d)
                        rankD.setTextColor(ContextCompat.getColor(this@ScanFoodDetailActivity, android.R.color.white))
                    }
                    'E' -> {
                        rankE.background = ContextCompat.getDrawable(this@ScanFoodDetailActivity, R.drawable.food_rank_e)
                        rankE.setTextColor(ContextCompat.getColor(this@ScanFoodDetailActivity, android.R.color.black))
                    }
                }
            }
        }

    }
}

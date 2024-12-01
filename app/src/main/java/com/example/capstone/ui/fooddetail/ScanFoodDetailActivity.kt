package com.example.capstone.ui.fooddetail

import android.os.Bundle
import android.widget.CheckBox
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.capstone.R
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

        setupView()
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

    private fun setupView() {

    }
}

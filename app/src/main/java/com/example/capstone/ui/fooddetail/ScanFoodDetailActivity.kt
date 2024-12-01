package com.example.capstone.ui.fooddetail

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.CheckBox
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.capstone.R
import com.example.capstone.data.Result
import com.example.capstone.data.remote.response.DataAnalyze
import com.example.capstone.databinding.ActivityScanFoodDetailBinding
import com.example.capstone.pref.SessionManager
import com.example.capstone.ui.factory.ViewModelFactory
import com.example.capstone.utils.reduceFileImage
import com.example.capstone.utils.uriToFile
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody

class ScanFoodDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityScanFoodDetailBinding
    private val selectedCategories = mutableListOf<String>()
    private lateinit var sessionManager: SessionManager
    private val viewModel: ScanFoodDetailViewModel by viewModels {
        ViewModelFactory.getInstance(this)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityScanFoodDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        sessionManager = SessionManager(this)
        val token = sessionManager.getAuthToken().toString()

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

        binding.btnSave.setOnClickListener {
            saveFood(token, imageUriString, analyzeData)
        }
        observeViewModel()

    }

    private fun observeViewModel() {
       viewModel.isLoading.observe(this) { isLoading ->
           showLoading(isLoading)
       }
        viewModel.saveResult.observe(this) { result ->
            when(result) {
                is Result.Loading -> showLoading(true)
                is Result.Error -> {
                    Log.e("SaveAnalyzeFood", result.error)
                    Toast.makeText(this, result.error, Toast.LENGTH_SHORT).show()
                }
                is Result.Success -> {
                    Toast.makeText(this, result.data.message, Toast.LENGTH_SHORT).show()
                    finish()
                }
            }
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun saveFood(token: String, imageUriString: String?, analyzeData: DataAnalyze?) {
        val imageUri = Uri.parse(imageUriString)
        val file = uriToFile(imageUri!!, this).reduceFileImage()
        val requestImageFile = file.asRequestBody("image/jpeg".toMediaTypeOrNull())
        val image = MultipartBody.Part.createFormData(
            "image",
            file.name,
            requestImageFile
        )

        val foodName = binding.edtFoodName.text.toString().trim()
        if (foodName.isEmpty()) {
            Toast.makeText(this, getString(R.string.harap_masukan_nama_makanan), Toast.LENGTH_SHORT).show()
            return
        }
        val tags = selectedCategories.joinToString(", ")
        val grade = analyzeData?.grade ?: 'C'
        val nutriscore = analyzeData?.nutriscore ?: 0.0
        val calories = analyzeData?.calories ?: 0.0
        val fat = analyzeData?.fat ?: 0.0
        val sugar = analyzeData?.sugar ?: 0.0
        val fiber = analyzeData?.fiber ?: 0.0
        val protein = analyzeData?.protein ?: 0.0
        val natrium = analyzeData?.natrium ?: 0.0
        val vegetable = analyzeData?.vegetable ?: 0.0
        val foodRateString = binding.foodRate.text.toString()
        val foodRate = foodRateString.toIntOrNull() ?: 1

        viewModel.saveAnalyzeFood(
            token,
            image,
            foodName,
            nutriscore,
            grade,
            tags,
            calories,
            fat,
            sugar,
            fiber,
            protein,
            natrium,
            vegetable,
            foodRate
        )
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

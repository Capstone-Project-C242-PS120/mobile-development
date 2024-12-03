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
import okhttp3.RequestBody.Companion.toRequestBody

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
        if (imageUriString.isNullOrEmpty()) {
            Toast.makeText(this, getString(R.string.gambar_tidak_ada), Toast.LENGTH_SHORT).show()
            return
        }

        val imageUri = Uri.parse(imageUriString)
        val file = uriToFile(imageUri, this).reduceFileImage()
        val requestImageFile = file.asRequestBody("image/jpeg".toMediaTypeOrNull())
        val image = MultipartBody.Part.createFormData("image", file.name, requestImageFile)

        val foodName = binding.edtFoodName.text.toString().trim()
        if (foodName.isEmpty()) {
            Toast.makeText(this, getString(R.string.harap_masukan_nama_makanan), Toast.LENGTH_SHORT).show()
            return
        }

        val nameRequestBody = foodName.toRequestBody("text/plain".toMediaTypeOrNull())
        val tagsRequestBody = selectedCategories.joinToString(", ").toRequestBody("text/plain".toMediaTypeOrNull())
        val gradeRequestBody = (analyzeData?.grade ?: 'C').toString().toRequestBody("text/plain".toMediaTypeOrNull())
        val nutriscoreRequestBody = (analyzeData?.nutriscore ?: 0.0).toString().toRequestBody("text/plain".toMediaTypeOrNull())
        val caloriesRequestBody = (analyzeData?.calories ?: 0.0).toString().toRequestBody("text/plain".toMediaTypeOrNull())
        val fatRequestBody = (analyzeData?.fat ?: 0.0).toString().toRequestBody("text/plain".toMediaTypeOrNull())
        val sugarRequestBody = (analyzeData?.sugar ?: 0.0).toString().toRequestBody("text/plain".toMediaTypeOrNull())
        val fiberRequestBody = (analyzeData?.fiber ?: 0.0).toString().toRequestBody("text/plain".toMediaTypeOrNull())
        val proteinRequestBody = (analyzeData?.protein ?: 0.0).toString().toRequestBody("text/plain".toMediaTypeOrNull())
        val natriumRequestBody = (analyzeData?.natrium ?: 0.0).toString().toRequestBody("text/plain".toMediaTypeOrNull())
        val vegetableRequestBody = (analyzeData?.vegetable ?: 0.0).toString().toRequestBody("text/plain".toMediaTypeOrNull())
        val foodRateRequestBody = binding.foodRate.text.toString().toIntOrNull()?.toString()?.toRequestBody("text/plain".toMediaTypeOrNull())
            ?: "1".toRequestBody("text/plain".toMediaTypeOrNull())

        viewModel.saveAnalyzeFood(
            token,
            image,
            nameRequestBody,
            nutriscoreRequestBody,
            gradeRequestBody,
            tagsRequestBody,
            caloriesRequestBody,
            fatRequestBody,
            sugarRequestBody,
            fiberRequestBody,
            proteinRequestBody,
            natriumRequestBody,
            vegetableRequestBody,
            foodRateRequestBody
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
                txtCalories.text = "${it.calories} kcal"
                txtProtein.text = "${it.protein} g"
                txtSugar.text = "${it.sugar} g"
                txtFat.text = "${it.fat} g"
                txtFiber.text = "${it.fiber} g"
                txtNatrium.text = "${it.natrium} mg"


                when(it.grade) {
                    'A' -> {
                        rankA.background = ContextCompat.getDrawable(this@ScanFoodDetailActivity, R.drawable.food_rank_a)
                        rankA.setTextColor(ContextCompat.getColor(this@ScanFoodDetailActivity, android.R.color.white))
                        binding.txtKeterangan.text = getString(R.string.keterangan_A)
                    }
                    'B' -> {
                        rankB.background = ContextCompat.getDrawable(this@ScanFoodDetailActivity, R.drawable.food_rank_b)
                        rankB.setTextColor(ContextCompat.getColor(this@ScanFoodDetailActivity, android.R.color.white))
                        binding.txtKeterangan.text = getString(R.string.keterangan_B)
                    }
                    'C' -> {
                        rankC.background = ContextCompat.getDrawable(this@ScanFoodDetailActivity, R.drawable.food_rank_c)
                        rankC.setTextColor(ContextCompat.getColor(this@ScanFoodDetailActivity, android.R.color.white))
                        binding.txtKeterangan.text = getString(R.string.keterangan_C)
                    }
                    'D' -> {
                        rankD.background = ContextCompat.getDrawable(this@ScanFoodDetailActivity, R.drawable.food_rank_d)
                        rankD.setTextColor(ContextCompat.getColor(this@ScanFoodDetailActivity, android.R.color.white))
                        binding.txtKeterangan.text = getString(R.string.keterangan_D)
                    }
                    'E' -> {
                        rankE.background = ContextCompat.getDrawable(this@ScanFoodDetailActivity, R.drawable.food_rank_e)
                        rankE.setTextColor(ContextCompat.getColor(this@ScanFoodDetailActivity, android.R.color.black))
                        binding.txtKeterangan.text = getString(R.string.keterangan_E)
                    }
                }
            }
        }

    }
}

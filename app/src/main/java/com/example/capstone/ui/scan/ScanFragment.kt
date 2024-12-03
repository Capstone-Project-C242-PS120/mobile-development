package com.example.capstone.ui.scan

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import com.example.capstone.R
import com.example.capstone.data.Result
import com.example.capstone.databinding.FragmentScanBinding
import com.example.capstone.pref.SessionManager
import com.example.capstone.ui.factory.ViewModelFactory
import com.example.capstone.ui.fooddetail.ScanFoodDetailActivity
import com.example.capstone.ui.subscriptionpage.SubscriptionActivity
import com.example.capstone.utils.getImageUri
import com.example.capstone.utils.reduceFileImage
import com.example.capstone.utils.uriToFile
import com.yalantis.ucrop.UCrop
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File

class ScanFragment : Fragment() {

    private var _binding: FragmentScanBinding? = null
    private val binding get() = _binding!!
    private var currentImageUri: Uri? = null
    private lateinit var sessionManager: SessionManager
    private val viewModel: ScanViewModel by viewModels {
        ViewModelFactory.getInstance(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentScanBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sessionManager = SessionManager(requireContext())
        val token = sessionManager.getAuthToken().toString()
        showImage()

        binding.btnCamera.setOnClickListener {
            startCamera()
        }
        binding.btnAnalyze.setOnClickListener {
            analyzeFood(token)
        }

        binding.btnPro.setOnClickListener {
            val intent = Intent(requireContext(), SubscriptionActivity::class.java)
            startActivity(intent)
        }
        viewModel.getQuota(token)
        observeViewModel()
    }

    private fun observeViewModel() {
        viewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            showLoading(isLoading)
        }
        viewModel.analyzeResult.observe(viewLifecycleOwner) { result ->
            when(result) {
                is Result.Loading -> showLoading(true)
                is Result.Error -> Toast.makeText(requireContext(), result.error, Toast.LENGTH_SHORT).show()
                is Result.Success -> {
                    val analyzeData = result.data.data
                    val intent = Intent(requireContext(), ScanFoodDetailActivity::class.java).apply {
                        putExtra("ANALYZE_DATA", analyzeData)
                        putExtra("IMAGE_URI", currentImageUri.toString())
                    }
                    startActivity(intent)
                }
            }
        }
        viewModel.quotaResult.observe(viewLifecycleOwner) { result ->
            when(result) {
                is Result.Loading -> {
                    showLoading(true)
                }
                is Result.Error -> {
                    Toast.makeText(requireContext(), result.error, Toast.LENGTH_SHORT).show()
                }
                is Result.Success -> {
                    binding.txtQuotaScan.text = result.data.data.scanQuota
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        sessionManager = SessionManager(requireContext())
        val token = sessionManager.getAuthToken().toString()
        viewModel.getQuota(token)
    }
    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun analyzeFood(token: String) {
        try {
            if (currentImageUri == null) {
                val message = getString(R.string.ambil_gambar_terlebih_dahulu)
                Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
                return
            }

            val file = uriToFile(currentImageUri!!, requireContext()).reduceFileImage()

            val requestImageFile = file.asRequestBody("image/jpeg".toMediaTypeOrNull())

            val multipartBody = MultipartBody.Part.createFormData(
                "image",
                file.name,
                requestImageFile
            )

            viewModel.analyzeFood(token, multipartBody)

        } catch (e: Exception) {

            Toast.makeText(requireContext(), "Error: ${e.message}", Toast.LENGTH_SHORT).show()
        }
    }


    private fun startCamera() {
        currentImageUri = getImageUri(requireContext())
        launcherIntentCamera.launch(currentImageUri!!)
    }

    private val launcherIntentCamera = registerForActivityResult(
        ActivityResultContracts.TakePicture()
    ) { isSuccess ->
        if (isSuccess) {
            currentImageUri?.let { uri ->
                startCrop(uri)
            }
        } else {
            currentImageUri = null
        }
    }

    private fun startCrop(uri: Uri) {
        val destinationUri = Uri.fromFile(File(requireContext().cacheDir, "cropped_${System.currentTimeMillis()}.jpg"))
        val uCrop = UCrop.of(uri, destinationUri)
        uCrop.withAspectRatio(3f, 4f)
        uCrop.withMaxResultSize(1080, 1440)
        uCrop.useSourceImageAspectRatio()
        uCrop.withOptions(UCrop.Options().apply {
            setFreeStyleCropEnabled(true)
        })

        val intent = uCrop.getIntent(requireContext())
        cropActivityResultLauncher.launch(intent)
    }

    private val cropActivityResultLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val data = result.data
            val resultUri = data?.let { UCrop.getOutput(it) }
            if ( resultUri != null) {
                currentImageUri = resultUri
                showImage()
            } else {
                Toast.makeText(requireContext(),
                    getString(R.string.tidak_ada_foto), Toast.LENGTH_SHORT).show()
            }
        } else {
            if ( result.resultCode == Activity.RESULT_CANCELED) {

            } else {
                val cropError = result.data?.let { UCrop.getError(it) }
                cropError?.let {
                    Toast.makeText(requireContext(),
                        getString(R.string.crop_gagal, it.message), Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun showImage() {
        currentImageUri?.let { uri ->
            binding.previewImage.setImageURI(uri)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


}
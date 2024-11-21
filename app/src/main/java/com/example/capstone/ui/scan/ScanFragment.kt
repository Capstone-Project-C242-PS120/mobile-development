package com.example.capstone.ui.scan

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.example.capstone.R
import com.example.capstone.databinding.FragmentScanBinding
import com.example.capstone.ui.fooddetail.FoodDetailActivity
import com.example.capstone.utils.getImageUri
import com.yalantis.ucrop.UCrop
import java.io.File

class ScanFragment : Fragment() {

    private var _binding: FragmentScanBinding? = null
    private val binding get() = _binding!!
    private var currentImageUri: Uri? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentScanBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        showImage()

        binding.btnCamera.setOnClickListener {
            startCamera()
        }
        binding.btnAnalyze.setOnClickListener {
            startActivity(Intent(requireContext(), FoodDetailActivity::class.java))
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
                Toast.makeText(requireContext(), "Tidak ada foto", Toast.LENGTH_SHORT).show()
            }
        } else {
            if ( result.resultCode == Activity.RESULT_CANCELED) {

            } else {
                val cropError = result.data?.let { UCrop.getError(it) }
                cropError?.let {
                    Toast.makeText(requireContext(), "Crop Gagal: ${it.message}", Toast.LENGTH_SHORT).show()
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
package com.example.capstone.ui.profile

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.capstone.R
import com.example.capstone.data.Result
import com.example.capstone.databinding.FragmentProfileBinding
import com.example.capstone.pref.SessionManager
import com.example.capstone.ui.factory.ViewModelFactory
import com.example.capstone.ui.subscriptionpage.SubscriptionActivity
import com.example.capstone.ui.tukarpoint.TukarPointActivity
import com.example.capstone.ui.welcome.WelcomeActivity


class ProfileFragment : Fragment() {

    private lateinit var binding: FragmentProfileBinding
    private lateinit var sessionManager: SessionManager

    private val profileViewModel: ProfileViewModel by viewModels {
        ViewModelFactory.getInstance(requireContext())
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        sessionManager = SessionManager(requireContext())

        binding.switchDarkMode.isChecked = sessionManager.isDarkModeEnabled()

        binding.switchDarkMode.setOnCheckedChangeListener{_, isChecked ->
            if (isChecked) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                sessionManager.setDarkModeEnabled(true)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                sessionManager.setDarkModeEnabled(false)
            }
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val token = sessionManager.getAuthToken()
        if (token != null) {
            profileViewModel.getProfile(token)
            observeViewModel()
        } else {
            navigateToWelcome()
        }

        binding.subsplanNav.setOnClickListener {
            navigateToSubscription()
        }

        binding.redeemPointNav.setOnClickListener {
            navigateToReedem()
        }

        binding.btnLogout.setOnClickListener {
            showLogoutConfirmation()
        }


    }

    private fun observeViewModel() {
        profileViewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            showLoading(isLoading)
        }
        profileViewModel.profileResult.observe(viewLifecycleOwner) { result ->
            when(result) {
                is Result.Success -> {
                    showLoading(false)
                    val profileData = result.data.data
                    binding.txtName.text = profileData.name
                    binding.txtEmail.text = profileData.email
                    binding.txtPoint.text = "${profileData.point} Point"
                }
                is Result.Error -> {
                    showLoading(false)
                    Toast.makeText(requireContext(), "Error: ${result.error}", Toast.LENGTH_SHORT).show()
                }
                is Result.Loading -> {
                    showLoading(true)
                }
            }
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun navigateToSubscription() {
        val intent = Intent(requireContext(), SubscriptionActivity::class.java)
        startActivity(intent)
    }

    private fun navigateToReedem(){
        val intent = Intent(requireContext(),TukarPointActivity::class.java)
        startActivity(intent)
    }

    private fun navigateToWelcome() {
        val intent = Intent(requireContext(), WelcomeActivity::class.java)
        startActivity(intent)
        requireActivity().finish()
    }

    private fun showLogoutConfirmation() {
        AlertDialog.Builder(requireContext())
            .setTitle(getString(R.string.konfirmasi_keluar))
            .setMessage(getString(R.string.apakah_kamu_ingin_keluar))
            .setPositiveButton(getString(R.string.ya)) { dialog, _ ->
                sessionManager.clearAuthToken()
                navigateToWelcome()
                dialog.dismiss()
            }
            .setNegativeButton(getString(R.string.tidak)) { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }

}
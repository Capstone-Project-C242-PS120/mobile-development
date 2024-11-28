package com.example.capstone.ui.explore

import android.os.Bundle
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.capstone.databinding.FragmentExploreBinding
import com.example.capstone.pref.SessionManager

class ExploreFragment : Fragment() {

    private var _binding: FragmentExploreBinding? = null
    private val binding get() = _binding!!
    private lateinit var sessionManager: SessionManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentExploreBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sessionManager = SessionManager(requireContext())
        val token = sessionManager.getAuthToken().toString()

        with(binding) {
            searchView.setupWithSearchBar(searchBar)
            searchView
                .editText
                .setOnEditorActionListener { textView, actionId, event ->
                    val inputText = textView.text.toString()
                    if (inputText.isNotBlank()) {
                        searchBar.setText(inputText)
                        searchView.hide() // Tutup SearchView
                        Toast.makeText(requireContext(), "Mencari $inputText", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(requireContext(), "Input tidak boleh kosong!", Toast.LENGTH_SHORT).show()
                    }
                    true // Mengindikasikan event sudah ditangani
                }

            // Menambahkan rekomendasi nama makanan sebagai TextView
            val recommendations = listOf<TextView>(
                recommendation1, recommendation2, recommendation3 // Gantilah ID dengan ID TextView di XML Anda
            )

            recommendations.forEach { textView ->
                textView.setOnClickListener {
                    val selectedText = textView.text.toString()

                    // Mengubah teks dalam SearchView menggunakan editText
                    searchView.editText?.setText(selectedText)

                    // Mengubah teks dalam SearchBar juga
                    searchBar.setText(selectedText)

                    searchView.hide() // Menutup SearchView setelah memilih teks
                    Toast.makeText(requireContext(), "Mencari $selectedText", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

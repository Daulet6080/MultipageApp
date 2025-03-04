package com.example.multi_pageapp.fragment

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
import com.example.multi_pageapp.databinding.FragmentIntentsBinding

class IntentsFragment : Fragment() {

    private var _binding: FragmentIntentsBinding? = null
    private val binding get() = _binding!!

    companion object {
        private const val INSTAGRAM_PACKAGE = "com.instagram.android"
        private const val INTENT_TYPE = "image/*" // Изменено на image/*, чтобы выбрать любые изображения
    }

    // Лаунчер для выбора изображения из галереи
    private val pickImageLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        uri?.let {
            shareToInstagram(it)
        } ?: Toast.makeText(requireContext(), "Не удалось выбрать изображение", Toast.LENGTH_SHORT).show()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentIntentsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.share.setOnClickListener {
            pickImageLauncher.launch("image/*") // Открываем галерею для выбора изображения
        }
    }

    private fun shareToInstagram(uri: Uri) {
        try {
            val shareIntent = Intent("com.instagram.share.ADD_TO_STORY").apply {
                setDataAndType(uri, INTENT_TYPE)
                addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                setPackage(INSTAGRAM_PACKAGE)
            }

            startActivity(shareIntent)
        } catch (e: Exception) {
            Toast.makeText(requireContext(), "Instagram не установлен или не поддерживает сторис", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

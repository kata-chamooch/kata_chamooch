package com.kata_chamooch.ui.bazar

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.kata_chamooch.data.DataRepository
import com.kata_chamooch.databinding.FragmentBazarBinding

class BazarFragment : Fragment() {

    private var _binding: FragmentBazarBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentBazarBinding.inflate(inflater, container, false)
        binding.progressBar.visibility = View.VISIBLE
        DataRepository.getBazarImageLink(::loadImageFromUrl)
        return binding.root
    }

    private fun loadImageFromUrl(videoUrl: String?) {
        if (videoUrl != null) {
            Glide.with(this)
                .load(videoUrl)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(binding.image)
        } else {
            Toast.makeText(requireContext(), "Error occurred! please try again", Toast.LENGTH_LONG)
                .show()
        }
        binding.progressBar.visibility = View.GONE

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
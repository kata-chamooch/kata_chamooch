package com.kata_chamooch.ui.morning

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.kata_chamooch.core.DateManager
import com.kata_chamooch.data.DataRepository
import com.kata_chamooch.databinding.FragmentMorningOffBinding

class MorningOffFragment : Fragment() {

    private var _binding: FragmentMorningOffBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMorningOffBinding.inflate(inflater, container, false)

        handleViewClick()
        return binding.root
    }

    private fun handleViewClick() {
        binding.launchCheckImg.setOnClickListener {
            it.isSelected = !it.isSelected
            binding.launchCrossImg.isSelected = false;
        }

        binding.launchCrossImg.setOnClickListener {
            it.isSelected = !it.isSelected
            binding.launchCheckImg.isSelected = false;
        }

        binding.dinnerCheckImg.setOnClickListener {
            it.isSelected = !it.isSelected
            binding.dinnerCrossImg.isSelected = false;
        }

        binding.dinnerCrossImg.setOnClickListener {
            it.isSelected = !it.isSelected
            binding.dinnerCheckImg.isSelected = false;
        }

        binding.snacksCheckImg.setOnClickListener {
            it.isSelected = !it.isSelected
            binding.snacksCrossImg.isSelected = false;
        }

        binding.snacksCrossImg.setOnClickListener {
            it.isSelected = !it.isSelected
            binding.snacksCheckImg.isSelected = false;
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
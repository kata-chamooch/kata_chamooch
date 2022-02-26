package com.kata_chamooch.ui.personalise

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.kata_chamooch.databinding.FragmentPersonaliseBinding
import androidx.appcompat.app.AppCompatActivity




class PersonaliseFragment : Fragment() {

    private var _binding: FragmentPersonaliseBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPersonaliseBinding.inflate(inflater, container, false)
        (activity as AppCompatActivity?)?.supportActionBar?.title = "Personalise your own"

        val root: View = binding.root

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
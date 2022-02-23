package com.kata_chamooch.ui.personalise

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.kata_chamooch.databinding.FragmentPersonaliseBinding

class PersonaliseFragment : Fragment() {

    private lateinit var personaliseViewModel: PersonaliseViewModel
    private var _binding: FragmentPersonaliseBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        personaliseViewModel =
                ViewModelProvider(this).get(PersonaliseViewModel::class.java)

        _binding = FragmentPersonaliseBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textNotifications
        personaliseViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
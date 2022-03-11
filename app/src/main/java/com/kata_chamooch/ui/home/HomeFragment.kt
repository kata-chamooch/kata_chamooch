package com.kata_chamooch.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.kata_chamooch.databinding.FragmentHomeBinding
import android.content.Intent
import com.kata_chamooch.core.Constant
import com.kata_chamooch.ui.WebActivity


class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.userGuide.setOnClickListener {
            val intent = Intent(activity, WebActivity::class.java)
            intent.putExtra(Constant.KEY_GUIDE_URL,
                "https://www.journaldev.com/14207/android-passing-data-between-fragments")
            startActivity(intent)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
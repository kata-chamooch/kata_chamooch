package com.kata_chamooch.ui.home

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.kata_chamooch.core.Constant
import com.kata_chamooch.core.DateManager
import com.kata_chamooch.data.local.AppPreference
import com.kata_chamooch.databinding.FragmentHomeBinding
import com.kata_chamooch.prefs
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
            intent.putExtra(
                Constant.KEY_GUIDE_URL,
                "https://docs.google.com/document/d/12WvyjqlY5-_PrPPKWJuv0vp4NVIpBa5wxsxoL2dc_CA/edit?usp=sharing"
            )
            startActivity(intent)
        }

        getTodayPoint()
        getTotalPoint()
    }

    private fun getTotalPoint() {
        var getTodayPoint =
            prefs.getIntData(DateManager.getTodayDateAsString() + AppPreference.USER_TODAY_ITEM_POINT)
        var getYesterdayPoint =
            prefs.getIntData(DateManager.getYesterdayDateString() + AppPreference.USER_TODAY_ITEM_POINT)

        if (getTodayPoint == -1) getTodayPoint = 0;
        if (getYesterdayPoint == -1) getYesterdayPoint = 0;

        val totalPointFloat: Float = (getTodayPoint + getYesterdayPoint) / 8f
        val totalPoint: Int = (totalPointFloat * 100).toInt()
        if (totalPoint > 0) {
            binding.totalPoint.text = ("$totalPoint/100")
        }
    }

    private fun getTodayPoint() {
        val point =
            prefs.getIntData(DateManager.getTodayDateAsString() + AppPreference.USER_TODAY_POINT)
        Log.d("pointCounter", "todays point $point")

        if (point > 0) {
            binding.todayPoint.text = ("$point/100")
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
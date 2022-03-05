package com.kata_chamooch.ui.dinner

import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.kata_chamooch.core.DateManager
import com.kata_chamooch.data.local.AppPreference
import com.kata_chamooch.databinding.FragmentDinnerOffBinding
import com.kata_chamooch.prefs
import java.util.concurrent.TimeUnit

private var _binding: FragmentDinnerOffBinding? = null
private val binding get() = _binding!!

private var countDownTimer: CountDownTimer? = null
private var timerStartFlag: Boolean = false

class DinnerOffFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentDinnerOffBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getTimeFromPref()
        handleViewClick()
    }

    private fun getTimeFromPref() {
        val startedTime = prefs.getStringData(AppPreference.DINNER_OFF_START_TIME)
        if (!startedTime.isNullOrEmpty()) {
            val getDiff = DateManager.getDifference(startedTime, DateManager.getTodayAsString())
            Log.d("Logger", "getTimeFromPref: $getDiff")
            if (getDiff > 0) {
                val totalDif = TimeUnit.HOURS.toMillis(16) - getDiff
                handleCounterDownTimer(totalDif, false)
                timerStartFlag = true
            }
        }
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

        binding.startCounterBtn.setOnClickListener {
            handleCounterDownTimer(TimeUnit.HOURS.toMillis(16), true)
        }
        binding.stopCounterBtn.setOnClickListener {
            resetTimer()
        }
    }

    private fun resetTimer() {
        Log.d("Logger", "resetTimer: called")
        binding.countdownTxt.text = "00:00:00"
        timerStartFlag = false
        countDownTimer?.cancel()
        prefs.clearPref(AppPreference.DINNER_OFF_START_TIME)
    }

    private fun handleCounterDownTimer(duration: Long, shouldSaveStartTime: Boolean) {
        if (timerStartFlag) {
            return
        }
        timerStartFlag = true
        countDownTimer = object : CountDownTimer(duration, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                val hms = String.format(
                    "%02d:%02d:%02d",
                    TimeUnit.MILLISECONDS.toHours(
                        millisUntilFinished
                    ),
                    TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished) - TimeUnit.HOURS.toMinutes(
                        TimeUnit.MILLISECONDS.toHours(
                            millisUntilFinished
                        )
                    ),
                    TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) - TimeUnit.MINUTES.toSeconds(
                        TimeUnit.MILLISECONDS.toMinutes(
                            millisUntilFinished
                        )
                    )
                )
                binding.countdownTxt.text = hms
            }

            override fun onFinish() {
                resetTimer()
            }
        }.start()
        if (shouldSaveStartTime) {
            prefs.setStringData(
                AppPreference.DINNER_OFF_START_TIME,
                DateManager.getTodayAsString()
            )
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        timerStartFlag = false
        countDownTimer?.cancel()
        _binding = null
    }
}
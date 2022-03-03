package com.kata_chamooch.ui.dinner

import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.kata_chamooch.databinding.FragmentDinnerOffBinding
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
        handleViewClick()
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
            handleCounterDownTimer(16 * 60 * 60 * 1000)
        }
        binding.stopCounterBtn.setOnClickListener {
            resetTimer()
        }
    }

    private fun resetTimer() {
        binding.countdownTxt.text = "00:00:00"
        timerStartFlag = false
        countDownTimer?.cancel()
    }

    private fun handleCounterDownTimer(duration: Long) {
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
    }


    override fun onDestroyView() {
        super.onDestroyView()
        countDownTimer?.onFinish()
        _binding = null
    }
}
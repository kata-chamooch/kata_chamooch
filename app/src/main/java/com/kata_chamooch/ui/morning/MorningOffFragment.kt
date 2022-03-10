package com.kata_chamooch.ui.morning

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.kata_chamooch.core.DateManager
import com.kata_chamooch.core.DateManager.getTodayAsString
import com.kata_chamooch.data.DataRepository
import com.kata_chamooch.data.local.AppPreference
import com.kata_chamooch.databinding.FragmentMorningOffBinding
import com.kata_chamooch.prefs
import java.util.concurrent.TimeUnit


private const val type = "morning-off"

class MorningOffFragment : Fragment() {
    private var _binding: FragmentMorningOffBinding? = null

    private val binding get() = _binding!!

    private var countDownTimer: CountDownTimer? = null
    private var timerStartFlag: Boolean = false
    private lateinit var datePrefix: String

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMorningOffBinding.inflate(inflater, container, false)
        datePrefix = DateManager.getTodayPrefix()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        DataRepository.getFoodItemsFromDb(datePrefix, type, ::setDataToTheView)
        checkPreference()
        getTimeFromPref()
        handleViewClick()
    }

    private fun setDataToTheView(data: Map<String, String>?, msg: String?) {
        binding.progressBar.visibility = View.GONE
        binding.mainLayout.visibility = View.VISIBLE

        data?.let {
            binding.apply {
                launchMenu.text = data["launch"]
                dinnerMenu.text = data["dinner"]
                snacksMenu.text = data["snacks"]
            }
        }

        msg?.let {
            Toast.makeText(requireActivity(), it, Toast.LENGTH_LONG).show()
        }
    }

    private fun getTimeFromPref() {
        val startedTime = prefs.getStringData(AppPreference.MORNING_OFF_START_TIME)
        if (!startedTime.isNullOrEmpty()) {
            val getDiff = DateManager.getDifference(startedTime, getTodayAsString())
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
            binding.launchCrossImg.isSelected = false
        }

        binding.launchCrossImg.setOnClickListener {
            it.isSelected = !it.isSelected
            binding.launchCheckImg.isSelected = false
        }

        binding.dinnerCheckImg.setOnClickListener {
            it.isSelected = !it.isSelected
            binding.dinnerCrossImg.isSelected = false
        }

        binding.dinnerCrossImg.setOnClickListener {
            it.isSelected = !it.isSelected
            binding.dinnerCheckImg.isSelected = false
        }

        binding.snacksCheckImg.setOnClickListener {
            it.isSelected = !it.isSelected
            binding.snacksCrossImg.isSelected = false
        }

        binding.snacksCrossImg.setOnClickListener {
            it.isSelected = !it.isSelected
            binding.snacksCheckImg.isSelected = false
        }

        binding.startCounterBtn.setOnClickListener {
            handleCounterDownTimer(TimeUnit.HOURS.toMillis(16), true)
        }
        binding.stopCounterBtn.setOnClickListener {
            resetTimer()
        }

        binding.videoLinkBtn.setOnClickListener {
            DataRepository.getWorkOutVideoLink(datePrefix, ::redirectUserToVideoPage)
        }
        binding.saveBtn.setOnClickListener {
            calculateUserPoint()
        }
    }

    private fun checkPreference() {
        val morningOffBF =
            prefs.getIntData(DateManager.getTodayDateAsString() + AppPreference.MORNING_OFF_DI)
        if (morningOffBF != -1) {
            binding.dinnerCrossImg.isEnabled = false
            binding.dinnerCheckImg.isEnabled = false
            if (morningOffBF == 1) {
                binding.dinnerCheckImg.isSelected = true
            } else {
                binding.dinnerCrossImg.isSelected = true
            }
        }

        val morningOffLA =
            prefs.getIntData(DateManager.getTodayDateAsString() + AppPreference.MORNING_OFF_LA)
        if (morningOffLA != -1) {
            binding.launchCheckImg.isEnabled = false
            binding.launchCrossImg.isEnabled = false
            if (morningOffLA == 1) {
                binding.launchCheckImg.isSelected = true
            } else {
                binding.launchCrossImg.isSelected = true
            }
        }

        val morningOffSN =
            prefs.getIntData(DateManager.getTodayDateAsString() + AppPreference.MORNING_OFF_SN)
        if (morningOffSN != -1) {
            binding.snacksCheckImg.isEnabled = false
            binding.snacksCrossImg.isEnabled = false
            if (morningOffSN == 1) {
                binding.snacksCheckImg.isSelected = true
            } else {
                binding.snacksCrossImg.isSelected = true
            }
        }

        val morningOffWO =
            prefs.getIntData(DateManager.getTodayDateAsString() + AppPreference.MORNING_OFF_WO)
        if (morningOffWO != -1) {
            binding.checkbox.isEnabled = false
            if (morningOffWO == 1) {
                binding.checkbox.isChecked = true
            }
        }
    }


    private fun calculateUserPoint() {
        var point = 0
        var changeDetect = 0
        if (binding.dinnerCheckImg.isSelected && binding.dinnerCheckImg.isEnabled) {
            point++
            changeDetect++
            prefs.setIntData(DateManager.getTodayDateAsString() + AppPreference.MORNING_OFF_DI, 1)
        }
        if (binding.dinnerCrossImg.isSelected && binding.dinnerCrossImg.isEnabled) {
            changeDetect++
            prefs.setIntData(DateManager.getTodayDateAsString() + AppPreference.MORNING_OFF_DI, 0)
        }

        if (binding.launchCheckImg.isSelected && binding.launchCheckImg.isEnabled) {
            point++
            changeDetect++
            prefs.setIntData(DateManager.getTodayDateAsString() + AppPreference.MORNING_OFF_LA, 1)
        }
        if (binding.launchCrossImg.isSelected && binding.launchCrossImg.isEnabled) {
            changeDetect++
            prefs.setIntData(DateManager.getTodayDateAsString() + AppPreference.MORNING_OFF_LA, 0)
        }


        if (binding.snacksCheckImg.isSelected && binding.snacksCheckImg.isEnabled) {
            point++
            changeDetect++
            prefs.setIntData(DateManager.getTodayDateAsString() + AppPreference.MORNING_OFF_SN, 1)
        }
        if (binding.snacksCrossImg.isSelected && binding.snacksCrossImg.isEnabled) {
            changeDetect++
            prefs.setIntData(DateManager.getTodayDateAsString() + AppPreference.MORNING_OFF_SN, 0)
        }
        if (binding.checkbox.isChecked && binding.checkbox.isEnabled) {
            point++
            changeDetect++
            prefs.setIntData(DateManager.getTodayDateAsString() + AppPreference.MORNING_OFF_WO, 1)
        }
        Log.d("pointCounter", "calculateUserPoint: $point")

        if (changeDetect > 0) {
            checkPreference()
            Toast.makeText(requireContext(), "Data saved successfully", Toast.LENGTH_LONG)
                .show()
        } else {
            Toast.makeText(requireContext(), "No change found !", Toast.LENGTH_LONG)
                .show()
        }
    }

    private fun redirectUserToVideoPage(videoId: String?) {
        if (videoId != null) {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube://$videoId"))
            startActivity(intent)
        } else {
            Toast.makeText(requireContext(), "Error occurred! please try again", Toast.LENGTH_LONG)
                .show()
        }
    }

    private fun resetTimer() {
        Log.d("Logger", "resetTimer: called")
        binding.countdownTxt.text = "00:00:00"
        timerStartFlag = false
        countDownTimer?.cancel()
        prefs.clearPref(AppPreference.MORNING_OFF_START_TIME)
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
            prefs.setStringData(AppPreference.MORNING_OFF_START_TIME, getTodayAsString())
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        timerStartFlag = false
        countDownTimer?.cancel()
        _binding = null
    }
}
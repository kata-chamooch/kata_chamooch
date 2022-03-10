package com.kata_chamooch.ui.dinner

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
import com.kata_chamooch.data.DataRepository
import com.kata_chamooch.data.local.AppPreference
import com.kata_chamooch.databinding.FragmentDinnerOffBinding
import com.kata_chamooch.prefs
import java.util.concurrent.TimeUnit

private var _binding: FragmentDinnerOffBinding? = null
private val binding get() = _binding!!

private var countDownTimer: CountDownTimer? = null
private var timerStartFlag: Boolean = false

private const val type = "dinner-off"
private lateinit var datePrefix: String

class DinnerOffFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentDinnerOffBinding.inflate(inflater, container, false)
        datePrefix = DateManager.getTodayPrefix()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        DataRepository.getFoodItemsFromDb("fri", type, ::setDataToTheView)
        checkIsDinnerMethodRunning()
        checkPreference()
        getTimeFromPref()
        handleViewClick()
    }

    private fun checkIsDinnerMethodRunning() {
        val method =
            prefs.getIntData(DateManager.getTodayDateAsString() + AppPreference.USER_SELECTED_METHOD)
        if (method == 1) {
            //user chose dinner off today
            //disable all
            Toast.makeText(
                requireContext(),
                "You already chose Morning-Off for today!",
                Toast.LENGTH_LONG
            )
                .show()
            binding.morningCheckImg.isEnabled = false
            binding.morningCrossImg.isEnabled = false
            binding.launchCheckImg.isEnabled = false
            binding.launchCrossImg.isEnabled = false
            binding.snacksCheckImg.isEnabled = false
            binding.snacksCrossImg.isEnabled = false
            binding.checkbox.isEnabled = false
            binding.saveBtn.isEnabled = false
            binding.startCounterBtn.isEnabled = false
            binding.stopCounterBtn.isEnabled = false
            binding.videoLinkBtn.isEnabled = false
            resetTimer()
        }
    }

    private fun setDataToTheView(data: Map<String, String>?, msg: String?) {
        binding.progressBar.visibility = View.GONE
        binding.mainLayout.visibility = View.VISIBLE

        data?.let {
            binding.apply {
                morningMenu.text = data["breakfast"]
                launchMenu.text = data["launch"]
                snacksMenu.text = data["snacks"]
            }
        }

        msg?.let {
            Toast.makeText(requireActivity(), it, Toast.LENGTH_LONG).show()
        }
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
            binding.launchCrossImg.isSelected = false
        }

        binding.launchCrossImg.setOnClickListener {
            it.isSelected = !it.isSelected
            binding.launchCheckImg.isSelected = false
        }

        binding.morningCheckImg.setOnClickListener {
            it.isSelected = !it.isSelected
            binding.morningCrossImg.isSelected = false
        }

        binding.morningCrossImg.setOnClickListener {
            it.isSelected = !it.isSelected
            binding.morningCheckImg.isSelected = false
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
        val dinnerOffBF =
            prefs.getIntData(DateManager.getTodayDateAsString() + AppPreference.DINNER_OFF_BF)
        if (dinnerOffBF != -1) {
            binding.morningCheckImg.isEnabled = false
            binding.morningCrossImg.isEnabled = false
            if (dinnerOffBF == 1) {
                binding.morningCheckImg.isSelected = true
            } else {
                binding.morningCrossImg.isSelected = true
            }
        }

        val dinnerOffLA =
            prefs.getIntData(DateManager.getTodayDateAsString() + AppPreference.DINNER_OFF_LA)
        if (dinnerOffLA != -1) {
            binding.launchCheckImg.isEnabled = false
            binding.launchCrossImg.isEnabled = false
            if (dinnerOffLA == 1) {
                binding.launchCheckImg.isSelected = true
            } else {
                binding.launchCrossImg.isSelected = true
            }
        }

        val dinnerOffSN =
            prefs.getIntData(DateManager.getTodayDateAsString() + AppPreference.DINNER_OFF_SN)
        if (dinnerOffSN != -1) {
            binding.snacksCheckImg.isEnabled = false
            binding.snacksCrossImg.isEnabled = false
            if (dinnerOffSN == 1) {
                binding.snacksCheckImg.isSelected = true
            } else {
                binding.snacksCrossImg.isSelected = true
            }
        }

        val dinnerOffWO =
            prefs.getIntData(DateManager.getTodayDateAsString() + AppPreference.DINNER_OFF_WO)
        if (dinnerOffWO != -1) {
            binding.checkbox.isEnabled = false
            if (dinnerOffWO == 1) {
                binding.checkbox.isChecked = true
            }
        }
    }


    private fun calculateUserPoint() {
        var point = 0
        var changeDetect = 0
        if (binding.morningCheckImg.isSelected && binding.morningCheckImg.isEnabled) {
            point++
            changeDetect++
            prefs.setIntData(DateManager.getTodayDateAsString() + AppPreference.DINNER_OFF_BF, 1)
        }
        if (binding.morningCrossImg.isSelected && binding.morningCrossImg.isEnabled) {
            changeDetect++
            prefs.setIntData(DateManager.getTodayDateAsString() + AppPreference.DINNER_OFF_BF, 0)
        }

        if (binding.launchCheckImg.isSelected && binding.launchCheckImg.isEnabled) {
            point++
            changeDetect++
            prefs.setIntData(DateManager.getTodayDateAsString() + AppPreference.DINNER_OFF_LA, 1)
        }
        if (binding.launchCrossImg.isSelected && binding.launchCrossImg.isEnabled) {
            changeDetect++
            prefs.setIntData(DateManager.getTodayDateAsString() + AppPreference.DINNER_OFF_LA, 0)
        }


        if (binding.snacksCheckImg.isSelected && binding.snacksCheckImg.isEnabled) {
            point++
            changeDetect++
            prefs.setIntData(DateManager.getTodayDateAsString() + AppPreference.DINNER_OFF_SN, 1)
        }
        if (binding.snacksCrossImg.isSelected && binding.snacksCrossImg.isEnabled) {
            changeDetect++
            prefs.setIntData(DateManager.getTodayDateAsString() + AppPreference.DINNER_OFF_SN, 0)
        }
        if (binding.checkbox.isChecked && binding.checkbox.isEnabled) {
            point++
            changeDetect++
            prefs.setIntData(DateManager.getTodayDateAsString() + AppPreference.DINNER_OFF_WO, 1)
        }
        Log.d("pointCounter", "calculateUserPoint: $point")

        if (changeDetect > 0) {
            checkPreference()
            prefs.setIntData(
                DateManager.getTodayDateAsString() + AppPreference.USER_SELECTED_METHOD,
                2
            )
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
package com.kata_chamooch.ui.personalise

import android.os.Bundle
import android.os.Message
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.kata_chamooch.data.DataRepository
import com.kata_chamooch.data.model.PersonaliseData
import com.kata_chamooch.databinding.FragmentPersonaliseBinding


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

        handleViews()
        return binding.root
    }

    private fun handleViews() {
        binding.apply {
            submitBTN.setOnClickListener {
                val name = nameEd.text.toString().trim()
                if (name.isEmpty()) {
                    nameEd.error = "Name is required!"
                    return@setOnClickListener
                }

                val number = contactNumED.text.toString().trim()
                if (number.isEmpty()) {
                    contactNumED.error = "Contact number is required!"
                    return@setOnClickListener
                } else if (number.length < 11 && number.length != 13) {
                    contactNumED.error = "Invalid contact number!"
                    return@setOnClickListener
                }

                val address = addressED.text.toString().trim()

                val hasDiabetes = diabetesCB.isChecked
                val hasHighPressure = highPressureCB.isChecked

                val otherConditions = otherInfoED.text.toString().trim()

                val personaliseData = PersonaliseData(
                    name = name,
                    contact = number,
                    address = address,
                    hasDiabetes = hasDiabetes,
                    hasHighPressure = hasHighPressure,
                    otherConditions = otherConditions
                )

                DataRepository.postUserPersonaliseData(personaliseData, ::postCallback)
            }
        }
    }

    private fun postCallback(message: String){
        Toast.makeText(requireActivity(), message, Toast.LENGTH_SHORT).show()
        clearAllField()
    }

    private fun clearAllField() {
        binding.apply {
            nameEd.setText("")
            contactNumED.setText("")
            addressED.setText("")
            otherInfoED.setText("")
            diabetesCB.isChecked = false
            highPressureCB.isChecked = false
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
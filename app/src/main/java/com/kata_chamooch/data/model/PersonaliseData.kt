package com.kata_chamooch.data.model

data class PersonaliseData(
    val name: String,
    val contact: String,
    val address: String,
    val hasDiabetes: Boolean,
    val hasHighPressure: Boolean,
    val otherConditions: String,
    val status: Boolean = false
)

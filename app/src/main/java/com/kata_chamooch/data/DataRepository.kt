package com.kata_chamooch.data

import android.util.Log
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.kata_chamooch.data.model.PersonaliseData

object DataRepository {

    private val mainRef: FirebaseDatabase = FirebaseDatabase.getInstance()

    fun getMainRef(): FirebaseDatabase = mainRef

    fun getFoodItemsFromDb() {
        val ref = mainRef.getReference("dinner-off").child("fri")

        val menuListener = object : ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {
                val user = snapshot.value as? String
                Log.d("retData", user ?: "")
            }

            override fun onCancelled(error: DatabaseError) {
                Log.d("retData", error.message + " " + error.details)
            }

        }

        ref.addListenerForSingleValueEvent(menuListener)
    }

    fun postUserPersonaliseData(personaliseData: PersonaliseData, callback: (msg: String) -> Unit) {
        mainRef.getReference("user-info").child(personaliseData.contact)
            .setValue(personaliseData).addOnCompleteListener {
                if (it.isSuccessful) {
                    val msg = "Data saved successfully"
                    Log.d("TAG", msg)
                    callback(msg)
                } else {
                    val msg = it.exception?.message ?: "Error occurred! try again please"
                    Log.d("TAG", msg)
                    callback(msg)
                }
            }
    }
}
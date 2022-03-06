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

    fun getFoodItemsFromDb(
        datePrefix: String,
        type: String,
        callback: (data: Map<String, String>?, msg: String?) -> Unit
    ) {
        val ref = mainRef.getReference("meal").child(datePrefix).child(type)

        val menuListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val data = dataSnapshot.value as? Map<String, String>
                callback(data, null)
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Log.d("retData", databaseError.message + " " + databaseError.details)
                callback(null, databaseError.message)
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
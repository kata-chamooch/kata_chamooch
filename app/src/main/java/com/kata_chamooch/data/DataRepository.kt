package com.kata_chamooch.data

import android.util.Log
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

object DataRepository {

    fun getFoodItemsFromDb(){
        val ref = FirebaseDatabase.getInstance().getReference("dinner-off").child("fri")

        val menuListener = object : ValueEventListener{

            override fun onDataChange(snapshot: DataSnapshot) {
                val user = snapshot.value as String
                Log.d("retData", user)
            }

            override fun onCancelled(error: DatabaseError) {
                Log.d("retData", error.message+" "+error.details)
            }

        }

        ref.addListenerForSingleValueEvent(menuListener)
    }
}
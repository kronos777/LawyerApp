package com.example.lawyerapp.presentation.viewmodel

import android.content.ContentValues.TAG
import android.util.Log
import android.widget.Toast
import com.google.android.material.internal.ContextUtils.getActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class UserViewModel {

    private lateinit var auth: FirebaseAuth

    fun changePassword(newPassword: String) {
        auth = Firebase.auth
        auth = FirebaseAuth.getInstance()
        val user = Firebase.auth.currentUser

        if (user != null) {
            Log.d("currentuser", user.toString())
            user!!.updatePassword(newPassword)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Log.d("currentuser", "пароль успешно сменен")
                    }
                }
        } else {
            Log.d("currentuser", "user.email.toString()")
        }
    }





}
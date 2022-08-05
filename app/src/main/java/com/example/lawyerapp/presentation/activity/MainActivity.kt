package com.example.lawyerapp.presentation.activity

import android.content.ContentValues.TAG
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.lawyerapp.R
import com.example.lawyerapp.databinding.ActivityMainBinding
import com.example.lawyerapp.presentation.fragments.SignInFragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity(), SignInFragment.OnEditingFinishedListener {
    private lateinit var binding: ActivityMainBinding
    // create Firebase authentication object
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialising auth object
        auth = Firebase.auth

        launchFragment(SignInFragment())


        //binding.buttonRegistration.setOnClickListener {
            //signUpUser()
           //login()
            //uploadData()
            //readDataFireStore()
      //  }


    }


    private fun launchFragment(fragment: Fragment) {
        this.supportFragmentManager.popBackStack()
        this.supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_item_container, fragment)
            .addToBackStack(null)
            .commit()
    }

    private fun login() {
        val email = "i.ziborov2018@yandex.ru"
        val pass = "123456"
        // calling signInWithEmailAndPassword(email, pass)
        // function using Firebase auth object
        // On successful response Display a Toast
        auth.signInWithEmailAndPassword(email, pass).addOnCompleteListener(this) {
            if (it.isSuccessful) {
                Toast.makeText(this, "Successfully LoggedIn", Toast.LENGTH_SHORT).show()
            //    binding.textView2.text = "Successfully login"
            } else
                Toast.makeText(this, "Log In failed ", Toast.LENGTH_SHORT).show()
        }
    }


    private fun signUpUser() {
        val email = "i.ziborov2018@yandex.ru"
        val pass = "123456"
        val confirmPassword = "123456"

        // check pass
        if (email.isBlank() || pass.isBlank() || confirmPassword.isBlank()) {
            Toast.makeText(this, "Email and Password can't be blank", Toast.LENGTH_SHORT).show()
            return
        }

        if (pass != confirmPassword) {
            Toast.makeText(this, "Password and Confirm Password do not match", Toast.LENGTH_SHORT)
                .show()
            return
        }
        // If all credential are correct
        // We call createUserWithEmailAndPassword
        // using auth object and pass the
        // email and pass in it.
        auth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener(this) {
            if (it.isSuccessful) {
                Toast.makeText(this, "Successfully Singed Up", Toast.LENGTH_SHORT).show()
             //   binding.textView2.text = "Successfully Singed Up"
                finish()
            } else {
                Toast.makeText(this, "Singed Up Failed!", Toast.LENGTH_SHORT).show()
            }
        }
    }


    private fun uploadData() {

            // create a dummy data
            val hashMap = hashMapOf<String, Any>(
                "name" to "Chmel",
                "lastname" to "Lisisi",
                "gender" to "man",
                "id" to "iddata"
            )

            // use the add() method to create a document inside users collection
            FirebaseUtils().fireStoreDatabase.collection("users")
                .add(hashMap)
                .addOnSuccessListener {
                    Log.d(TAG, "Added document with ID ${it.id}")
                 //   binding.textView2.text = "Added document with ID ${it.id}"
                }
                .addOnFailureListener { exception ->
                    Log.w(TAG, "Error adding document $exception")
                }

   }


    private fun readDataFireStore() {
        FirebaseUtils().fireStoreDatabase.collection("users")
            .get()
            .addOnSuccessListener { querySnapshot ->
                //querySnapshot.forEach { document ->
                    //Log.d(TAG, "Read document with ID ${document.id}")
                    for (result in querySnapshot) {
//                        val users = result.toObject(User::class.java)
                        Log.d(TAG, "${result.id} => ${result.data}")
                    }
                //}
            }
            .addOnFailureListener { exception ->
                Log.w(TAG, "Error getting documents $exception")
            }
    }

    override fun onEditingFinished() {
        TODO("Not yet implemented")
    }

}

class FirebaseUtils {
    val fireStoreDatabase = FirebaseFirestore.getInstance()
}

data class User (val name: String, val lastname: String, val gender: String, val id: String)
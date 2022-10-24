package com.example.lawyerapp.presentation.fragments

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.lawyerapp.databinding.*
import com.example.lawyerapp.domain.message.MobileNumber
import com.example.lawyerapp.domain.message.UserProfile
import com.example.lawyerapp.presentation.helpers.OnEditingFinishedListener
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions

class ChatFirstTestFragment: Fragment() {

    private lateinit var onEditingFinishedListener: OnEditingFinishedListener

    private var _binding: FragmentChatFirstTestBinding? = null
    private val binding: FragmentChatFirstTestBinding
        get() = _binding ?: throw RuntimeException("FragmentChatFirstTestBinding == null")

    private val usersCollection = FirebaseFirestore.getInstance()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentChatFirstTestBinding.inflate(inflater, container, false)
       return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //(activity as AppCompatActivity).supportActionBar?.title = "Войти"
        //(activity as AppCompatActivity).supportActionBar?.subtitle = "Зарегистрироваться"
       // makeQuery(arrayListOf("+7 (926) 425-6325", "+7 (965) 364-5236"))
       // Log.d("resultQuery", queriedList.toString())
        makeQuery()
    }


    fun makeQuery() {
        usersCollection.collection("Users")
            //.whereEqualTo("phone", true)
            .get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    Log.d("resultQuery", "${document.id} => ${document.data.get("phone")}")
                }
            }
            .addOnFailureListener { exception ->
                Log.w("resultQuery", "Error getting documents: ", exception)
            }
    }


   /* fun insertUser()  {
        val mobileNumber= MobileNumber("+7","9266543210")

        val profile = UserProfile(
            uId= userId,
            image = "https://i.pravatar.cc/150?img=2",
            userName = "otis",
            mobile = mobileNumber,
            token = "el3LZrv0QmK5k-vjoO970:APA91bF8XKRqqTmf2n7E2PUrVDaxvhxq3WAIwQ5sHqLkwGbiICkebrCpkpwJvrVrvcJiDgaeLndJQwJDWvv2ad-W0SGDpDyuYgrQGFo5eDQSvce20gS1Q5ijmkD-QmzBAgFoRVaDi2u",
        )


        val db = FirebaseFirestore.getInstance()
        db.collection("Users").document(profile.uId!!)
            .set(profile, SetOptions.merge())
            .addOnSuccessListener { Log.d(TAG, "DocumentSnapshot successfully written!") }
            .addOnFailureListener { e -> Log.w(TAG, "Error writing document", e) }
    }
*/
   companion object{
       var queriedList=ArrayList<String>()
       //var queriedList=ArrayList<UserProfile>()
       var currentQueryCount=0
       var totalQueryCount=0
   }

}

interface QueryListener{
    fun onCompleted(queriedList: ArrayList<UserProfile>)
    fun onStart(position: Int,contactBatch: ArrayList<String>)
}

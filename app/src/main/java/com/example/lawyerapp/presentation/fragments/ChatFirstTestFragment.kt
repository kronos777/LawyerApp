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
import com.example.lawyerapp.domain.message.Message
import com.example.lawyerapp.domain.message.MobileNumber
import com.example.lawyerapp.domain.message.TextMessage
import com.example.lawyerapp.domain.message.UserProfile
import com.example.lawyerapp.presentation.helpers.OnEditingFinishedListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.google.firebase.ktx.Firebase
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class ChatFirstTestFragment: Fragment() {

    private lateinit var onEditingFinishedListener: OnEditingFinishedListener

    private var _binding: FragmentChatFirstTestBinding? = null
    private val binding: FragmentChatFirstTestBinding
        get() = _binding ?: throw RuntimeException("FragmentChatFirstTestBinding == null")

    private lateinit var auth: FirebaseAuth

    private val usersCollection = FirebaseFirestore.getInstance()

    /*work chat*/
    private lateinit var database: DatabaseReference


    lateinit var myUserId: String
    lateinit var currentChatUser: String
    /*work chat*/


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
        auth = Firebase.auth
        database = Firebase.database.reference
        //(activity as AppCompatActivity).supportActionBar?.title = "Войти"
        //(activity as AppCompatActivity).supportActionBar?.subtitle = "Зарегистрироваться"
       // makeQuery(arrayListOf("+7 (926) 425-6325", "+7 (965) 364-5236"))
       // Log.d("resultQuery", queriedList.toString())
       // makeQuery()
     //createChat()
//        getDataTest()
        testMessage()
    }

    fun createTestMessage() {

    }


    fun getDataTest() {
        database.child("chats").child("s4VnOEw1MKU3g05YhAF8").get().addOnSuccessListener {
            Log.i("firebase", "Got value ${it.value}")
        }.addOnFailureListener{
            Log.e("firebase", "Error getting data", it)
        }
    }

    fun makeQuery() {
        usersCollection.collection("Users")
            //.whereEqualTo("phone", true)
            .get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    Log.d("resultQuery", "${document.id} => ${document.data.get("email")}")
                }
            }
            .addOnFailureListener { exception ->
                Log.w("resultQuery", "Error getting documents: ", exception)
            }

      //  Log.d("resultQuery", dateToUTC().toString())

    }

    fun createChat() {

        val fromTo = "fmailTomail2"
        val lead = "testLead"
        val createdAdd = dateToUTC()
        val read = 0
        val message = "first message"

        val dataCreateChat = TestChat(fromTo, lead, createdAdd, read, message)
        database.child("chats").child(generateId()).setValue(dataCreateChat)

    }

    fun createMessage(chatId: Long, msg: String): Message {
        TODO()
    }

    /*fun createMessage(msg: String): Message {
        return Message(id= generateId(),
            createdAt= dateToUtc(),
            from= myUserId,
            to= currentChatUser.id,
            chatUsers= listOf(myUserId,currentChatUser.id),
            textMessage= TextMessage(msg)
        )
    }*/

    fun generateId(length: Int= 20): String{ //ex: bwUIoWNCSQvPZh8xaFuz
        val alphaNumeric = ('a'..'z') + ('A'..'Z') + ('0'..'9')
        return alphaNumeric.shuffled().take(length).joinToString("")
    }


    fun dateToUTC(date: Date =Date()): String {
        // will be converted back to local time zone before showing in recycler view
        val formatterUTC: DateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
        formatterUTC.timeZone = TimeZone.getTimeZone("UTC")
        return formatterUTC.format(date)
    }


    fun testMessage() {
        val message = TestMessage("s4VnOEw1MKU3g05YhAF8", dateToUTC(), "ziswp1@gmail.com", 0, "i.ziborov2018@yandex.ru",
        "test message 1")

        val message2 = TestMessage("s4VnOEw1MKU3g05YhAF8", dateToUTC(), "i.ziborov2018@yandex.ru", 0, "ziswp1@gmail.com",
            "test message 2")

        database.child("message").child("s4VnOEw1MKU3g05YhAF8").child("ykfX0Wtu83SeIZfr9F1od1iOyfI3Togf6lmNvI5NZGcceShVS6ywENOxb2").setValue(message2)

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

data class TestChat(val fromTo: String, val lead: String, val createdAdd: String, val read: Int, val message: String)
data class TestMessage(
    val idChat: String,
    val createdAdd: String,
    val userFrom: String,
    val statusMessage: Int,
    val userTo: String,
    val message: String,
)
package com.example.lawyerapp.presentation.fragments

import android.app.Activity
import android.app.ProgressDialog
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.DocumentsContract
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.constraintlayout.helper.widget.MotionEffect.TAG
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.lawyerapp.R
import com.example.lawyerapp.databinding.FragmentSigninBinding
import com.example.lawyerapp.domain.UserClient
import com.example.lawyerapp.presentation.helpers.FirebaseUtils
import com.example.lawyerapp.presentation.helpers.OnEditingFinishedListener
import com.example.lawyerapp.presentation.helpers.PhoneTextFormatter
import com.firebase.ui.storage.images.FirebaseImageLoader
import com.google.android.material.appbar.MaterialToolbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.OnProgressListener
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.UploadTask
import com.squareup.picasso.Picasso


class SignInFragment() : Fragment(), OnEditingFinishedListener {


    private lateinit var onEditingFinishedListener: OnEditingFinishedListener

    private var _binding: FragmentSigninBinding? = null
    private val binding: FragmentSigninBinding
        get() = _binding ?: throw RuntimeException("FragmentSigninBinding == null")
    private lateinit var auth: FirebaseAuth
    private lateinit var storage: FirebaseStorage
    private lateinit var storageReference: StorageReference
   // private lateinit var ImageStore: DatabaseReference

    private val PICK_IMAGE_CODE = 2
    private var imageUri: Uri? = null
    private var isLawyer: Boolean? = null
    private lateinit var database: DatabaseReference
    private lateinit var listUrlFile: ArrayList<Uri>

    var PICK_IMAGE_MULTIPLE = 1
    lateinit var imagePath: String
    var imagesPathList: MutableList<String> = arrayListOf()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnEditingFinishedListener) {
            onEditingFinishedListener = context
        } else {
            throw RuntimeException("Activity must implement OnEditingFinishedListener")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       //parseParams()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSigninBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        auth = Firebase.auth
        auth = FirebaseAuth.getInstance()

        database = Firebase.database.reference
        /*ImageStore =
            FirebaseDatabase.getInstance().getReference().child("UserProfiles").child(
                auth.uid.toString()
            )
*/
        listUrlFile = ArrayList<Uri>()

        Toast.makeText(getActivity(), "user id " + auth.uid.toString(), Toast.LENGTH_SHORT).show()

        (activity as AppCompatActivity).findViewById<Toolbar>(R.id.tool_bar).title = "Регистрация"
        toolBarLink()
        hideLawyerFields()

        val items = listOf("Клиент", "Юрист")
        val adapter = ArrayAdapter(requireContext(), R.layout.list_item_role, items)
        (binding.etRole as? AutoCompleteTextView)?.setAdapter(adapter)

        // Минимальное число символов для показа выпадающего списка
        binding.etRole.threshold = 2

        // Обработчик щелчка
        binding.etRole.onItemClickListener = AdapterView.OnItemClickListener { parent, _,
                                                                               position, id ->
            val selectedItem = parent.getItemAtPosition(position).toString()
            // Выводим выбранное слово
            //Toast.makeText(getContext(), "Selected: $selectedItem", Toast.LENGTH_SHORT).show()
            if(selectedItem == "Юрист") {
                showLawyerFields()
                isLawyer = true


                binding.etDiplomData.setOnClickListener {
                    multipleChoiseImage()
                }



            } else {
                hideLawyerFields()
                isLawyer = false
            }


        }

        binding.etPhone.addTextChangedListener(PhoneTextFormatter(binding.etPhone, "+7 (###) ###-####"))

        storage = FirebaseStorage.getInstance()
        storageReference = storage.getReference()

        binding.saveButton.setOnClickListener {
            if(isLawyer == true) {
                signInUserLawyer()
            } else {
                signInUserClient()
            }

            //signUpUser()
        }

      /*  binding.textEnterAccount.setOnClickListener {
           // launchFragment(LoginFragment())
            uploadImage()
            //readDataImage()
        }*/
/*
        binding.imageView.setOnClickListener{
            pickFromGallery()
        }

        // Отслеживаем закрытие выпадающего списка
        autoCompleteTextView.setOnDismissListener {
            Toast.makeText(applicationContext, "Suggestion closed.", Toast.LENGTH_SHORT).show()
        }

        // Обработчик щелчка для корневого элемента макета (LinearLayout или др.)
        root_layout.setOnClickListener {
            val text = autoCompleteTextView.text
            Toast.makeText(applicationContext, "Inputted: $text", Toast.LENGTH_SHORT).show()
        }

        // Если к компоненту перешёл фокус
        autoCompleteTextView.onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                // Выводим выпадающий список
                autoCompleteTextView.showDropDown()
            }
        }
        addTextChangeListeners()
        launchRightMode()
        observeViewModel()*/

        getDatabaseData()

    }

    private fun signInUserLawyer() {
        val role = "client"
        val name = binding.etName.text.toString()
        val sername = binding.etSername.text.toString()
        val lastname = binding.etLastname.text.toString()
        val email = binding.etEmail.text.toString()
        val phone = binding.etPhone.text.toString()
        val password = binding.etPassword.text.toString()
        val confirmPassword = binding.etRepeatPassword.text.toString()
        val passport = binding.etPassportData.text.toString()
        val diplom = binding.etDiplomData.text.toString()
      //  val photo = binding.imageChoose.text.toString()
    }


    private fun multipleChoiseImage() {
        if (Build.VERSION.SDK_INT < 19) {
            var intent = Intent()
            intent.type = "image/*"
            intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
            intent.action = Intent.ACTION_GET_CONTENT
            startActivityForResult(
                Intent.createChooser(intent, "Select Picture")
                , PICK_IMAGE_MULTIPLE
            )
        } else {
            var intent = Intent(Intent.ACTION_OPEN_DOCUMENT)
            intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
            intent.addCategory(Intent.CATEGORY_OPENABLE)
            intent.type = "image/*"
            startActivityForResult(intent, PICK_IMAGE_MULTIPLE);
        }
    }


    private fun readDataImage() {
      /* storageReference.child("images/" + auth.uid.toString() + "/user_profile.jpg").getDownloadUrl().addOnSuccessListener {
           Toast.makeText(getActivity(), "Suggestion closed." + it.path, Toast.LENGTH_SHORT).show()
           Glide.with(this).load(it.path).into(binding.imageView)
       }*/

       // val path = ref.path
        /*storage.ref('image.jpg').getDownloadURL()
            .then((url) => {
                // Do something with the URL ...
            })*/

        //Toast.makeText(getActivity(), "Suggestion closed." + httpsReference, Toast.LENGTH_SHORT).show()
        Glide.with(this).load("https://firebasestorage.googleapis.com/v0/b/lawyerapp-88046.appspot.com/o/images%2FRdSFpZohlFdTzLcb0mOOJWZdWKq1%2Fuser_profile?alt=media&token=1dfafe53-d1d1-4f19-9f53-08b2c08bd1ae").into(binding.imageViewPassport)
        //Glide.with(this).load("http://goo.gl/gEgYUd").into(binding.imageView)

    }


    private fun uploadImages(paramsUpload: String) {
        for (url in listUrlFile.indices) {
            val imageUri = listUrlFile[url]
            if (imageUri != null) {
                val progressDialog = ProgressDialog(getActivity())
                progressDialog.setTitle("Uploading...")
                progressDialog.show()
                val ref: StorageReference =
                    storageReference.child("images/" + auth.uid.toString() + "/" + paramsUpload + "/" + "image" + url)
                ref.putFile(imageUri!!)
                    .addOnSuccessListener {
                        progressDialog.dismiss()
                        val downloadUri = it.task.snapshot.metadata?.path?.toUri()
                        //val downloadUri2 = it.task.snapshot.storage.downloadUrl
                        // val downloadUri = it.task.snapshot.storage.downloadUrl
                        //Toast.makeText(getActivity(), "Uploaded" + downloadUri.toString(), Toast.LENGTH_SHORT).show()
                        Log.d("uploadIri", downloadUri.toString())
                        //val downloadUri = it.d

                    }
                    .addOnFailureListener { e ->
                        progressDialog.dismiss()
                        Toast.makeText(getActivity(), "Failed " + e.message, Toast.LENGTH_SHORT)
                            .show()
                    }
                    .addOnProgressListener(object : OnProgressListener<UploadTask.TaskSnapshot?> {
                        override fun onProgress(taskSnapshot: UploadTask.TaskSnapshot) {
                            val progress = 100.0 * taskSnapshot.bytesTransferred / taskSnapshot
                                .totalByteCount
                            progressDialog.setMessage("Uploaded " + progress.toInt() + "%")
                        }
                    })

            }
        }
    }

    private fun uploadImage() {
        if (imageUri != null) {
            val progressDialog = ProgressDialog(getActivity())
            progressDialog.setTitle("Uploading...")
            progressDialog.show()
            val ref: StorageReference =
                storageReference.child("images/" + auth.uid.toString() + "/" + "user_profile")
            ref.putFile(imageUri!!)
                .addOnSuccessListener {
                    progressDialog.dismiss()
                    val downloadUri = it.task.snapshot.metadata?.path?.toUri()
                    //val downloadUri2 = it.task.snapshot.storage.downloadUrl
                   // val downloadUri = it.task.snapshot.storage.downloadUrl
                    //Toast.makeText(getActivity(), "Uploaded" + downloadUri.toString(), Toast.LENGTH_SHORT).show()
                    Log.d("uploadIri", downloadUri.toString())
                    //val downloadUri = it.d

                }
                .addOnFailureListener { e ->
                    progressDialog.dismiss()
                    Toast.makeText(getActivity(), "Failed " + e.message, Toast.LENGTH_SHORT)
                        .show()
                }
                .addOnProgressListener(object : OnProgressListener<UploadTask.TaskSnapshot?> {
                    override fun onProgress(taskSnapshot: UploadTask.TaskSnapshot) {
                        val progress = 100.0 * taskSnapshot.bytesTransferred / taskSnapshot
                            .totalByteCount
                        progressDialog.setMessage("Uploaded " + progress.toInt() + "%")
                    }
                })

        }

    }

    private fun getDatabaseData() {
        database.child("users").get().addOnSuccessListener {
            Log.d("firebase", "Got value ${it.value}")

        }.addOnFailureListener{
            Log.d("firebase", "Error getting data", it)
        }
    }

    private fun signInUserClient() {

        val role = "client"
        val name = binding.etName.text.toString()
        val sername = binding.etSername.text.toString()
        val email = binding.etEmail.text.toString()
        val phone = binding.etPhone.text.toString()
        val password = binding.etPassword.text.toString()
        val confirmPassword = binding.etRepeatPassword.text.toString()





        // check pass
        if (email.isBlank() || password.isBlank() || confirmPassword.isBlank()) {
            Toast.makeText(getActivity(), "Email and Password can't be blank", Toast.LENGTH_SHORT).show()
            return
        }


        if (password != confirmPassword) {
            Toast.makeText(getActivity(), "Password and Confirm Password do not match", Toast.LENGTH_SHORT)
                .show()
            return
        }

        getActivity()?.let {
            auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(it) {
                if (it.isSuccessful) {
                    val userCurrent = Firebase.auth.currentUser
                    userCurrent!!.sendEmailVerification()
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                val user = UserClient(role, name, sername, email, phone, "", password, userCurrent.uid)
                                database.child("users").child(userCurrent.uid).setValue(user)

                              //  Toast.makeText(getActivity(), "Регистрация прошла успешно на Ваш электронной почты отправлено сообщение.", Toast.LENGTH_SHORT).show()
                               // uploadImage()
                            }
                        }
                  //  getActivity()?.finish()
                } else {
                    Toast.makeText(getActivity(), "Регистрация прервана, ошибка регистрвции.", Toast.LENGTH_SHORT).show()
                }
            }
        }

    }

    private fun toolBarLink() {
        val materialToolbar: MaterialToolbar = (activity as AppCompatActivity).findViewById<Toolbar>(R.id.tool_bar) as MaterialToolbar
        materialToolbar.menu.getItem(0).title = "Войти"
        materialToolbar.setOnMenuItemClickListener {
            // Toast.makeText(this, "Favorites Clsadsaicked"+it.itemId, Toast.LENGTH_SHORT).show()
            when (it.itemId) {

                R.id.registration -> {
                    launchFragment(LoginFragment())
                    true
                }

                else -> false
            }
        }
    }


    private fun launchFragment(fragment: Fragment) {
        // this.supportFragmentManager.popBackStack()
        getActivity()?.supportFragmentManager?.beginTransaction()
            ?.replace(R.id.fragment_item_container, fragment)
            ?.addToBackStack("registration")
            ?.commit()
    }


    private fun showLawyerFields() {
        binding.tilLastname.visibility = (View.VISIBLE)
        binding.tilPassportData.visibility = (View.VISIBLE)
        binding.textPassportData.visibility = (View.VISIBLE)
        binding.tilDiplomData.visibility = (View.VISIBLE)
        binding.textDiplomData.visibility = (View.VISIBLE)
        binding.imageViewPassport.visibility =  (View.VISIBLE)
        binding.imageViewDiplom.visibility =  (View.VISIBLE)
        binding.textDiplomData.visibility =  (View.VISIBLE)
        binding.textPassportData.visibility =  (View.VISIBLE)
    }

    private fun hideLawyerFields() {
        binding.tilLastname.visibility = (View.GONE)
        binding.tilPassportData.visibility = (View.GONE)
        binding.textPassportData.visibility = (View.GONE)
        binding.tilDiplomData.visibility = (View.GONE)
        binding.textDiplomData.visibility = (View.GONE)
        binding.imageViewPassport.visibility = (View.GONE)
        binding.imageViewDiplom.visibility = (View.GONE)
        binding.textDiplomData.visibility = (View.GONE)
        binding.textPassportData.visibility = (View.GONE)
    }

    private fun signUpUser() {
        val email = "i.ziborov2018@yandex.ru"
        val pass = "123456"
        val confirmPassword = "123456"

        // check pass
        if (email.isBlank() || pass.isBlank() || confirmPassword.isBlank()) {
            Toast.makeText(getActivity(), "Email and Password can't be blank", Toast.LENGTH_SHORT).show()
            return
        }

        if (pass != confirmPassword) {
            Toast.makeText(getActivity(), "Password and Confirm Password do not match", Toast.LENGTH_SHORT)
                .show()
            return
        }
        // If all credential are correct
        // We call createUserWithEmailAndPassword
        // using auth object and pass the
        // email and pass in it.
        getActivity()?.let {
            auth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener(it) {
                if (it.isSuccessful) {
                    //Toast.makeText(this, "Successfully Singed Up", Toast.LENGTH_SHORT).show()
                    //   binding.textView2.text = "Successfully Singed Up"
                    getActivity()?.finish()
                } else {
                    //Toast.makeText(this, "Singed Up Failed!", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }


    private fun createUserLawyerData() {

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
                Log.d(ContentValues.TAG, "Added document with ID ${it.id}")
                //   binding.textView2.text = "Added document with ID ${it.id}"
            }
            .addOnFailureListener { exception ->
                Log.w(ContentValues.TAG, "Error adding document $exception")
            }

    }

    private fun pickFromGallery() {
        val getIntent = Intent(Intent.ACTION_GET_CONTENT)
        getIntent.type = "image/*"

        val pickIntent = Intent(
            Intent.ACTION_PICK,
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        )
        pickIntent.type = "image/*"

        val chooserIntent = Intent.createChooser(getIntent, "выберите фото")
        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, arrayOf(pickIntent))
        startActivityForResult(chooserIntent, PICK_IMAGE_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == PICK_IMAGE_CODE && resultCode == Activity.RESULT_OK) {
            if (data != null) {
                imageUri = data.data
               // viewModel.imageUri = imageUri
                Picasso.get().load(imageUri)
                    .into(binding.imageViewPassport)
            }
        } else if (requestCode == PICK_IMAGE_MULTIPLE && resultCode == Activity.RESULT_OK
            && null != data
        ) {
            if (data.getClipData() != null) {
                var count = data.clipData?.itemCount
                for (i in 0..count!! - 1) {
                    var imageUri: Uri = data.clipData?.getItemAt(i)!!.uri
                    listUrlFile.add(imageUri)
                    Log.d("imagePath", imageUri.toString())
                    getPathFromURI(imageUri)
                }
            } else if (data.getData() != null) {
                var imagePath: String = data.data?.path!!
                Log.e("imagePath", imagePath)
            }

            Log.d("imageArrayList", listUrlFile.toString())
            uploadImages("diplomdata")
           // displayImageData()
        }

    }

    private fun displayImageData() {
        TODO("Not yet implemented")
    }

    private fun getPathFromURI(uri: Uri) {
        var path: String = uri.path!! // uri = any content Uri

        val databaseUri: Uri
        val selection: String?
        val selectionArgs: Array<String>?
        if (path.contains("/document/image:")) { // files selected from "Documents"
            databaseUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
            selection = "_id=?"
            selectionArgs = arrayOf(DocumentsContract.getDocumentId(uri).split(":")[1])
        } else { // files selected from all other sources, especially on Samsung devices
            databaseUri = uri
            selection = null
            selectionArgs = null
        }
        try {
            val projection = arrayOf(
                MediaStore.Images.Media.DATA,
                MediaStore.Images.Media._ID,
                MediaStore.Images.Media.ORIENTATION,
                MediaStore.Images.Media.DATE_TAKEN
            ) // some example data you can query
            val cursor = getActivity()?.contentResolver?.query(
                databaseUri,
                projection, selection, selectionArgs, null
            )
            if (cursor != null) {
                if (cursor.moveToFirst()) {
                    val columnIndex = cursor.getColumnIndex(projection[0])
                    imagePath = cursor.getString(columnIndex)
                    // Log.e("path", imagePath);
                    imagesPathList.add(imagePath)
                }
            }
            if (cursor != null) {
                cursor.close()
            }
        } catch (e: Exception) {
            Log.e(TAG, e.message, e)
        }
    }

    override fun onEditingFinished() {
        TODO("Not yet implemented")
    }

}
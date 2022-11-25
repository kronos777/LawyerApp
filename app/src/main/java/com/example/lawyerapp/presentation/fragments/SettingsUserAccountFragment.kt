package com.example.lawyerapp.presentation.fragments

import android.app.Activity.RESULT_OK
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.content.Intent.ACTION_PICK
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.provider.MediaStore.Images
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.canhub.cropper.CropImageContract
import com.canhub.cropper.CropImageView
import com.canhub.cropper.options
import com.example.lawyerapp.databinding.FragmentSettingUserAccountBinding
import com.example.lawyerapp.presentation.helpers.BottomTestGalleryFragment
import com.example.lawyerapp.presentation.helpers.OnEditingFinishedListener
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import java.io.ByteArrayOutputStream


class SettingsUserAccountFragment: Fragment() {

    private lateinit var onEditingFinishedListener: OnEditingFinishedListener

    private var _binding: FragmentSettingUserAccountBinding? = null
    private val binding: FragmentSettingUserAccountBinding
        get() = _binding ?: throw RuntimeException("FragmentAboutApplicationBinding == null")

    private val REQUEST_TAKE_PHOTO = 1
    val GALLERY_REQUEST = 1
    val PIC_CROP = 2
    private var picUri: Uri? = null


    private val cropImage = registerForActivityResult(CropImageContract()) { result ->
        if (result.isSuccessful) {
            // Use the returned uri.
            val uriContent = result.uriContent
            val uriFilePath = result.getUriFilePath(requireContext()) // optional usage
         //   Toast.makeText(activity, "img path" + uriContent.toString(), Toast.LENGTH_SHORT).show()
            val bitmap = MediaStore.Images.Media.getBitmap(requireActivity().contentResolver, uriContent)
            binding.imageView.setImageBitmap(bitmap)
        } else {
            // An error occurred.
            val exception = result.error
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSettingUserAccountBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.changeFotoProfile.setOnClickListener {
            val items = arrayOf("Сделать фото", "Выбрать из галереи", "Удалить", "Отмена")

            context?.let { it1 ->
                MaterialAlertDialogBuilder(it1)
                    //.setTitle(resources.getString(R.string.title))
                    .setItems(items) { dialog, which ->
                        // Respond to item chosen

                        when (which) {
                            0 -> getCameraImage()
                            1 -> getGalleryImage()
                            //1 -> testCrop()
                            2 -> Toast.makeText(getActivity(), "Ничего не делаем", Toast.LENGTH_SHORT).show()
                            //3 ->
                        }

                    }
                    .show()
            }
        }
        //(activity as AppCompatActivity).supportActionBar?.title = "Войти"
        //(activity as AppCompatActivity).supportActionBar?.subtitle = "Зарегистрироваться"
        deleteProfileUser()
    }


    private fun startCrop(uriFilePath: Uri) {
        // Start picker to get image for cropping and then use the image in cropping activity.
        cropImage.launch(
            options(uri = uriFilePath) {
                setGuidelines(CropImageView.Guidelines.ON)
                setOutputCompressFormat(Bitmap.CompressFormat.PNG)
            }
        )

        // Start picker to get image for cropping from only gallery and then use the image in cropping activity.
     /*  cropImage.launch(
            options {
                setImagePickerContractOptions(
                    PickImageContractOptions(includeGallery = true, includeCamera = false)
                )
            }
        )

        // Start cropping activity for pre-acquired image saved on the device and customize settings.
        cropImage.launch(
            options(uri = uriFilePath) {
                setGuidelines(CropImageView.Guidelines.ON)
                setOutputCompressFormat(Bitmap.CompressFormat.PNG)
            }
        )*/
    }


    private fun testCrop() {
        getActivity()?.let { BottomTestGalleryFragment().show(it.supportFragmentManager, "tag") }
    }

    private fun deleteProfileUser() {
        binding.aboutApplicationHref2Delete.setOnClickListener {

            getActivity()?.let { it1 ->
                MaterialAlertDialogBuilder(it1)
                    .setTitle("Вы действительно хотите удалить профиль?")
                    //.setCustomTitle(textViewTitle)
                    .setMessage("После удаления профиля все данные будут удалены безвозвратно")
                    .setNegativeButton("отмена") { dialog, which ->
                        // Respond to negative button press
                    }
                    .setPositiveButton("удалить") { dialog, which ->

                        // Respond to positive button press
                    }
                    .show()
            }
        }
    }



    private fun getCameraImage() {
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        try {
            startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO)
        } catch (e: ActivityNotFoundException) {
            e.printStackTrace()
        }
    }




    private fun getGalleryImage() {
        val photoPickerIntent = Intent(ACTION_PICK)
        photoPickerIntent.type = "image/*"
        startActivityForResult(photoPickerIntent, GALLERY_REQUEST)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_TAKE_PHOTO && resultCode == RESULT_OK) {
            // Фотка сделана, извлекаем миниатюру картинки
            if(data?.extras?.get("data") != null) {
               // val thumbnailBitmap = data?.extras?.get("data") as Bitmap
                Toast.makeText(activity, data?.extras?.get("data").toString(), Toast.LENGTH_SHORT).show()
                val imageUri: Uri? = getActivity()?.let { getImageUri(it.applicationContext,
                    data?.extras?.get("data") as Bitmap
                ) }

                if (imageUri != null) {
                    startCrop(imageUri)
                }
               // binding.imageView.setImageBitmap(thumbnailBitmap)
            } else {
                var bitmap: Bitmap? = null
                val imageUri: Uri? = data?.getData()
                if (imageUri != null) {
                    startCrop(imageUri)
                }
               // bitmap = MediaStore.Images.Media.getBitmap(requireActivity().contentResolver, imageUri)

              //  binding.imageView.setImageBitmap(bitmap)
            }

        }


    }


    fun getImageUri(inContext: Context, inImage: Bitmap): Uri? {
        val bytes = ByteArrayOutputStream()
        inImage.compress(Bitmap.CompressFormat.PNG, 100, bytes)
        val path = Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null)
        return Uri.parse(path)
    }


    private fun performCrop() {
        try {
            // Намерение для кадрирования. Не все устройства поддерживают его
            val cropIntent = Intent("com.android.camera.action.CROP")
            cropIntent.setDataAndType(picUri, "image/*")
            cropIntent.putExtra("crop", "true")
            cropIntent.putExtra("aspectX", 1)
            cropIntent.putExtra("aspectY", 1)
            cropIntent.putExtra("outputX", 256)
            cropIntent.putExtra("outputY", 256)
            cropIntent.putExtra("return-data", true)
            startActivityForResult(cropIntent, PIC_CROP)
        } catch (anfe: ActivityNotFoundException) {
            val errorMessage = "Извините, но ваше устройство не поддерживает кадрирование"
            val toast: Toast = Toast.makeText(getActivity(), errorMessage, Toast.LENGTH_SHORT)
            toast.show()
        }
    }
}
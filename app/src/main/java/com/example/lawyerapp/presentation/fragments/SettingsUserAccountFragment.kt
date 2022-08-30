package com.example.lawyerapp.presentation.fragments

import android.R
import android.app.Activity.RESULT_OK
import android.content.ActivityNotFoundException
import android.content.Intent
import android.content.Intent.ACTION_PICK
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.lawyerapp.databinding.FragmentSettingUserAccountBinding
import com.example.lawyerapp.presentation.helpers.OnEditingFinishedListener
import com.google.android.material.dialog.MaterialAlertDialogBuilder


class SettingsUserAccountFragment: Fragment() {

    private lateinit var onEditingFinishedListener: OnEditingFinishedListener

    private var _binding: FragmentSettingUserAccountBinding? = null
    private val binding: FragmentSettingUserAccountBinding
        get() = _binding ?: throw RuntimeException("FragmentAboutApplicationBinding == null")

    private val REQUEST_TAKE_PHOTO = 1
    val GALLERY_REQUEST = 1
    val PIC_CROP = 2
    private var picUri: Uri? = null
    val CAMERA_REQUEST = 1

    private val TAG: String = "AppDebug"

    private val GALLERY_REQUEST_CODE = 1234


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
            val items = arrayOf("Сделать фото", "Выбрать ", "Отмена")

            context?.let { it1 ->
                MaterialAlertDialogBuilder(it1)
                    //.setTitle(resources.getString(R.string.title))
                    .setItems(items) { dialog, which ->
                        // Respond to item chosen

                        when (which) {
                            0 -> getCameraImage()
                            1 -> getGalleryImage()
                            2 -> Toast.makeText(getActivity(), "Ничего не делаем", Toast.LENGTH_SHORT).show()
                        }

                    }
                    .show()
            }
        }
        //(activity as AppCompatActivity).supportActionBar?.title = "Войти"
        //(activity as AppCompatActivity).supportActionBar?.subtitle = "Зарегистрироваться"


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
                val thumbnailBitmap = data?.extras?.get("data") as Bitmap
                binding.imageView.setImageBitmap(thumbnailBitmap)
            } else {
                var bitmap: Bitmap? = null
                val imageUri: Uri? = data?.getData()



                bitmap = MediaStore.Images.Media.getBitmap(requireActivity().contentResolver, imageUri)
                binding.imageView.setImageBitmap(bitmap)
            }

        }


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
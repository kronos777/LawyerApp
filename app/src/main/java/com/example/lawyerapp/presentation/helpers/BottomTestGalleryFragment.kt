package com.example.lawyerapp.presentation.helpers

import android.Manifest
import android.database.Cursor
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.GridView
import android.widget.ListView
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.lawyerapp.R
import com.example.lawyerapp.databinding.BottomSheetLayoutBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlin.collections.ArrayList

private const val COLLAPSED_HEIGHT = 360
private const val MY_PERMISSIONS_REQUEST = 1234
private val PERMISSIONS = arrayOf<String>(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE)


class BottomTestGalleryFragment : BottomSheetDialogFragment() {
    //private lateinit var imageList: ListView
    private lateinit var imageList: GridView
    private var isGallery = false

    private var dataImageGalleryModel: ArrayList<DataImagesGalleryModel>? = null

    // Можно обойтись без биндинга и использовать findViewById
    lateinit var binding: BottomSheetLayoutBinding

    // Переопределим тему, чтобы использовать нашу с закруглёнными углами
    override fun getTheme() = R.style.AppBottomSheetDialogTheme

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = BottomSheetLayoutBinding.bind(inflater.inflate(R.layout.bottom_sheet_layout, container))
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        imageList = binding.listViewImage
    }

    // Я выбрал этот метод ЖЦ, и считаю, что это удачное место
    // Вы можете попробовать производить эти действия не в этом методе ЖЦ, а например в onCreateDialog()
    override fun onStart() {
        super.onStart()

        // Плотность понадобится нам в дальнейшем
        val density = requireContext().resources.displayMetrics.density
        Log.d("density", density.toString())

        dialog?.let {
            // Находим сам bottomSheet и достаём из него Behaviour
            val bottomSheet = it.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet) as FrameLayout
            val behavior = BottomSheetBehavior.from(bottomSheet)

            // Выставляем высоту для состояния collapsed и выставляем состояние collapsed
            behavior.peekHeight = (COLLAPSED_HEIGHT * density).toInt()
            behavior.state = BottomSheetBehavior.STATE_COLLAPSED

            // Достаём корневые лэйауты
            val coordinator = (it as BottomSheetDialog).findViewById<CoordinatorLayout>(com.google.android.material.R.id.coordinator)
            val containerLayout = it.findViewById<FrameLayout>(com.google.android.material.R.id.container)

            // Надуваем наш лэйаут с кнопкой
            val buttons = it.layoutInflater.inflate(R.layout.button_in_bsheet, null)

            // Выставояем параметры для нашей кнопки
            buttons.layoutParams = FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.WRAP_CONTENT
            ).apply {
                height = (60 * density).toInt()
                gravity = Gravity.BOTTOM
            }
            // Добавляем кнопку в контейнер
            containerLayout?.addView(buttons)

            // Перерисовываем лэйаут
            buttons.post {
                (coordinator?.layoutParams as ViewGroup.MarginLayoutParams).apply {
                    buttons.measure(
                        View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
                        View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
                    )
                    // Устраняем разрыв между кнопкой и скролящейся частью
                    this.bottomMargin = (buttons.measuredHeight - 8 * density).toInt()
                    containerLayout?.requestLayout()
                }
            }

          testGalleryImage()


           behavior.addBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {

                override fun onStateChanged(bottomSheet: View, newState: Int) {
                    // Нам не нужны действия по этому колбеку
                }

                override fun onSlide(bottomSheet: View, slideOffset: Float) {
                    with(binding) {
                        // Нас интересует только положительный оффсет, тк при отрицательном нас устроит стандартное поведение - скрытие фрагмента
                        if (slideOffset > 0) {
                            // Делаем "свёрнутый" layout более прозрачным
                            layoutCollapsed.alpha = 1 - 2 * slideOffset
                            // И в то же время делаем "расширенный layout" менее прозрачным
                            layoutExpanded.alpha = slideOffset * slideOffset

                            // Когда оффсет превышает половину, мы скрываем collapsed layout и делаем видимым expanded
                            if (slideOffset > 0.5) {
                                layoutCollapsed.visibility = View.GONE
                                layoutExpanded.visibility = View.VISIBLE
                            }

                            // Если же оффсет меньше половины, а expanded layout всё ещё виден, то нужно скрывать его и показывать collapsed
                            if (slideOffset < 0.5 && binding.layoutExpanded.visibility == View.VISIBLE) {
                                layoutCollapsed.visibility = View.VISIBLE
                                layoutExpanded.visibility = View.INVISIBLE
                            }
                        }
                    }
                }
            })
        }
    }

    fun testGalleryImage() {
        Log.i("Gallery","onResume ${isGallery}")
        if (!isGallery){


            val file = getCameraImages().reversed()

            Log.d("Gallery","${file.size}")
            // imageList.layoutManager = LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false)
            // imageList.adapter = AdapterImageGallery(file, requireContext().applicationContext)
            dataImageGalleryModel = ArrayList<DataImagesGalleryModel>()
                for (itemimg in file) {
                    dataImageGalleryModel!!.add(DataImagesGalleryModel(itemimg))
                    Log.d("imagepath", itemimg)
                }
            val adapterImages = AdapterImageGallery(dataImageGalleryModel!!, requireContext().applicationContext)
            imageList.adapter = adapterImages
            /*
			     -   listViewNotes = binding.listViewNotes
        dataNotesStudentModel = ArrayList<DataNotesStudentModel>()
        viewModelNotesItem = ViewModelProvider(this)[NotesItemViewModel::class.java]
        viewModelNotesItem.notesList.getNotesList().observe(viewLifecycleOwner) {

            for (item in it) {
                if(item.student == studentItemId) {
                    dataNotesStudentModel!!.add(DataNotesStudentModel(item.text, item.date))
                    Log.d("notes current student", item.text + item.date)
                 //   Toast.makeText(activity, "notes current student" + item.text + item.date, Toast.LENGTH_LONG).show()
                }
            }

            val adapterNotes = ListNotesAdapter(dataNotesStudentModel!!, requireContext().applicationContext)

            listViewNotes.adapter = adapterNotes
*/



            isGallery = true
        }
    }
    /*test code gallery*/
    override fun onResume() {
        super.onResume()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
        //if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && isPermissions()){
            requestPermissions(PERMISSIONS, MY_PERMISSIONS_REQUEST)
            return
        }

        Log.i("Gallery","onResume ${isGallery}")
        if (!isGallery){


            val file = getCameraImages().reversed()

            Log.d("Gallery","${file.size}")
           // imageList.layoutManager = LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false)
           // imageList.adapter = AdapterImageGallery(this,file)

            isGallery = true
        }
    }
/*
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == MY_PERMISSIONS_REQUEST && grantResults.isNotEmpty()){
            if (isPermissions()){
                (Objects.requireNonNull(getActivity().getSystemService(Context.ACTIVITY_SERVICE)) as ActivityManager).clearApplicationUserData()
                recreate()
            }
        }
    }

@SuppressLint("NewApi")
private fun isPermissions():Boolean{
    PERMISSIONS.forEach {
        if (checkSelfPermission(it) != PackageManager.PERMISSION_GRANTED){
            return true
        }
    }
    return false
}
*/

    fun getCameraImages(): List<String> {
        val uri: Uri = android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        val cursor: Cursor?
        val column_index_data: Int
        val column_index_folder_name: Int
        val listOfAllImages = ArrayList<String>()
        var absolutePathOfImage: String? = null

        val projection = arrayOf(MediaStore.MediaColumns.DATA, MediaStore.Images.Media.BUCKET_DISPLAY_NAME)

        cursor = activity?.contentResolver?.query(uri, projection, null,
            null, null)

        column_index_data = cursor!!.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA)
        column_index_folder_name = cursor
            .getColumnIndexOrThrow(MediaStore.Images.Media.BUCKET_DISPLAY_NAME)
        while (cursor.moveToNext()) {
            absolutePathOfImage = cursor.getString(column_index_data)
            listOfAllImages.add(absolutePathOfImage)
        }
        return listOfAllImages
    }

    /*test code gallery*/
}
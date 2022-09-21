package com.example.lawyerapp.presentation.helpers

import android.annotation.SuppressLint
import android.content.ContentUris
import android.content.Context
import android.database.Cursor
import android.graphics.Bitmap
import android.net.Uri
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import com.example.lawyerapp.R


class AdapterImageGallery (private val dataSet: ArrayList<*>, mContext: Context) :
    ArrayAdapter<Any?>(mContext, R.layout.image_gallery_item_bottom_sheet, dataSet) {
    private class ViewHolder {
        lateinit var srcImage: ImageView
    }

    var arrayList: ArrayList<Int> = ArrayList()


    override fun getCount(): Int {
        return dataSet.size
    }
    override fun getItem(position: Int): DataImagesGalleryModel {
        return dataSet[position] as DataImagesGalleryModel
    }
    override fun getView(
        position: Int,
        convertView: View?,
        parent: ViewGroup
    ): View {
        var convertView = convertView
        val viewHolder: ViewHolder
        val result: View
        if (convertView == null) {
            viewHolder = ViewHolder()
            convertView =
                LayoutInflater.from(parent.context).inflate(R.layout.image_gallery_item_bottom_sheet, parent, false)
            viewHolder.srcImage =
                convertView.findViewById(R.id.tv_image)
            result = convertView
            convertView.tag = viewHolder
        } else {
            viewHolder = convertView.tag as ViewHolder
            result = convertView
        }
        val item: DataImagesGalleryModel = getItem(position)
        //set img
        val srcUri = getMediaUriFromPath(context, item.src!!)
        val bitmap = MediaStore.Images.Media.getBitmap(context.contentResolver, srcUri)
        //val thumbnailBitmap = srcUri as Bitmap

        //set img
        viewHolder.srcImage.setImageBitmap(bitmap)

        return result
    }

    @SuppressLint("Range")
    fun getMediaUriFromPath(context: Context, path: String): Uri? {
        val mediaUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        val cursor: Cursor? = context.contentResolver.query(
            mediaUri,
            null,
            MediaStore.Images.Media.DISPLAY_NAME + "= ?",
            arrayOf(path.substring(path.lastIndexOf("/") + 1)),
            null
        )
        var uri: Uri? = null
        if (cursor!!.moveToFirst()) {
            uri = ContentUris.withAppendedId(
                mediaUri,
                cursor.getLong(cursor.getColumnIndex(MediaStore.Images.Media._ID))
            )
        }
        cursor.close()
        return uri
    }

}

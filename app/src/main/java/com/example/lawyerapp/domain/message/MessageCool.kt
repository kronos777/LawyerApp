package com.example.lawyerapp.domain.message


import android.os.Parcel
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.firebase.firestore.Exclude
import com.google.firebase.firestore.IgnoreExtraProperties
import kotlinx.parcelize.Parceler
import kotlinx.serialization.Serializable

/*
@IgnoreExtraProperties
@Serializable
@kotlinx.parcelize.Parcelize
@Entity
data class Message(
    @PrimaryKey
    val createdAt: Long, var deliveryTime: Long=0L,
    var seenTime: Long=0L,
    val from: String?, val to: String?,
    val senderName: String?,
    val senderImage: String?,
    var type: String? ="text",//0=text,1=audio,2=image,3=video,4=file
    var status: Int=0,//0=sending,1=sent,2=delivered,3=seen,4=failed
    var textMessage: TextMessage?=null,
    var imageMessage: ImageMessage?=null,
    var audioMessage: AudioMessage?=null,
    var videoMessage: VideoMessage?=null,
    var fileMessage: FileMessage?=null,
    var chatUsers: ArrayList<String>?=null,
    @set:Exclude @get:Exclude
    var chatUserId: String?=null): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readLong(),
        parcel.readLong(),
        parcel.readLong(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readInt(),
        parcel.readParcelable(TextMessage::class.java.classLoader),
        parcel.readParcelable(ImageMessage::class.java.classLoader),
        parcel.readParcelable(AudioMessage::class.java.classLoader),
        parcel.readParcelable(VideoMessage::class.java.classLoader),
        parcel.readParcelable(FileMessage::class.java.classLoader),
        TODO("chatUsers"),
        parcel.readString()
    ) {
    }

    companion object : Parceler<Message> {

        override fun Message.write(parcel: Parcel, flags: Int) {
            parcel.writeLong(createdAt)
            parcel.writeLong(deliveryTime)
            parcel.writeLong(seenTime)
            parcel.writeString(from)
            parcel.writeString(to)
            parcel.writeString(senderName)
            parcel.writeString(senderImage)
            parcel.writeString(type)
            parcel.writeInt(status)
            parcel.writeParcelable(textMessage, flags)
            parcel.writeParcelable(imageMessage, flags)
            parcel.writeParcelable(audioMessage, flags)
            parcel.writeParcelable(videoMessage, flags)
            parcel.writeParcelable(fileMessage, flags)
            parcel.writeString(chatUserId)
        }

        override fun create(parcel: Parcel): Message {
            return Message(parcel)
        }
    }
}

@Serializable
@kotlinx.parcelize.Parcelize
data class TextMessage(val text: String?=null): Parcelable

@Serializable
@kotlinx.parcelize.Parcelize
data class AudioMessage(var uri: String?=null,val duration: Int=0): Parcelable

@Serializable
@kotlinx.parcelize.Parcelize
data class ImageMessage(var uri: String?=null,var imageType: String="image"): Parcelable

@Serializable
@kotlinx.parcelize.Parcelize
data class VideoMessage(val uri: String?=null,val duration: Int=0): Parcelable

@Serializable
@kotlinx.parcelize.Parcelize
data class FileMessage(val name: String?=null,
                       val uri: String?=null,val duration: Int=0): Parcelable
*/
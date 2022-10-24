package com.example.lawyerapp.domain.message

import android.os.Parcelable
import com.google.firebase.database.IgnoreExtraProperties
import com.google.firebase.database.PropertyName
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Serializable
@IgnoreExtraProperties
@Parcelize
data class UserProfile(var uId: String?=null,var createdAt: Long?=null,
                       var updatedAt: Long?=null,
                       var image: String="", var userName: String="",
                       var about: String="",
                       var token :String="",
                       var mobile: ModelMobile?=null,
                       @get:PropertyName("device_details")
                       @set:PropertyName("device_details")
                       var deviceDetails: ModelDeviceDetails?=null) : Parcelable
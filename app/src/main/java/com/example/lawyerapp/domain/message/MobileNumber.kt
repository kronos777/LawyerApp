package com.example.lawyerapp.domain.message

import com.google.firebase.database.PropertyName

data class MobileNumber(
    @get:PropertyName("country_code")
    @set:PropertyName("country_code")
    var countryCode: String="",
    var number: String="")
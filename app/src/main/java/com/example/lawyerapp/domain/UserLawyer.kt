package com.example.lawyerapp.domain

data class UserLawyer(val role: String, val name: String, val sername: String, val patronymic: String, val phone: String, val email: String,
                      val passportData: List<String>, val diplomOfHigherEducation: List<String>, val photo: String, val password: String, val id: String)
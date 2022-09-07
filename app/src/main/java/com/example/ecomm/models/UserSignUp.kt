package com.example.ecomm.models

data class UserInfo(
    val ufname : String?,
    val ulname : String?,
    val uemail : String?,
    val uphone : String?,
    val udob : String?,
    val upassword : String?
)
data class UserInfoResponse(
    val fname : String?,
    val lname : String?,
    val email : String?,
    val phone : String?,
    val dob : String?,
    val password : String?,
    val id : String?,
)

data class Error(
    val error: String?
)

package com.example.ecomm.models

data class UserLogin(
    val uemail : String?,
    val upassword : String?
)

data class UserResponse(
    val message : String?,
    val userpresent:UserDetails?
)

data class UserDetails(
    val _id : String?,
    val fname : String?,
    val lname : String?,
    val email : String?,
    val phone : String?,
    val dob : String?,
    val password : String?

)

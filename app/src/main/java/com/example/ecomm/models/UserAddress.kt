package com.example.ecomm.models

data class UserAddress(
    val _id: String?,
    val addedBy : AddedBy?,
    val address : String?,
    val city : String?,
    val state : String?,
    val country : String?,
)
data class AddAddress(
    val addedBy : String?,
    val address : String?,
    val city : String?,
    val state : String?,
    val country : String?,
)
data class AddAddressResponse(
    val _id: String?,
    val addedBy : String?,
    val address : String?,
    val city : String?,
    val state : String?,
    val country : String?,
)
data class DeleteAddressResponse(
    val acknowledged: String?
)

data class AddedBy(
    val _id : String?,
    val fname : String?,
    val lname : String?,
    val email : String?,
    val phone : String?,
    val dob : String?,
    val password : String?

)

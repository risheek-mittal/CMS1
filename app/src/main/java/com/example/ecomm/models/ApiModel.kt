package com.example.ecomm.models

import com.google.gson.annotations.SerializedName

class ApiModel {
    var id: Int? = null
    var name: String? = null
    var brand: String? = null
    var category: String? = null
    var username: String? = null
//    var images: Product? = null
    var thumbnail: String?= null
    var price: Int?=null
    var stock: Int?=null
//    var discountPercentage: Float?=null
//    var rate: Float?=null
}

class Product {
    @SerializedName("0")
    var zero: String?=null
    @SerializedName("1")
    var one: String?=null
    @SerializedName("2")
    var two: String?=null
    @SerializedName("3")
    var three: String?=null
    @SerializedName("4")
    var four: String?=null
}
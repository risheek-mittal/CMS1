package com.example.ecomm.models

data class OrderModel(
    var uid: String,
    var order: ArrayList<ProductDetail>
)

data class ProductDetail(
    var pname: String,
    var category: String,
    var qty: Int,
    var price: Int,
    var tamount: Int,
)

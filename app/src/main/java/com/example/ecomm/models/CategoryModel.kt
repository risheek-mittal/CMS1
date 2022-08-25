package com.example.ecomm.models

import com.example.ecomm.objects.Constants

class CategoryParentModel(
    val parentTitle:String?=null,
    var type:Int = Constants.PARENT,
    var subList : MutableList<ChildData> = ArrayList(),
    var isExpanded:Boolean = false
)
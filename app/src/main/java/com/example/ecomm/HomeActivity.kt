package com.example.ecomm

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ecomm.models.CategoryParentModel
import com.example.ecomm.models.ChildData
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity() {

    val listData : MutableList<CategoryParentModel> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        val parentData: Array<String> = arrayOf("Andhra Pradesh", "Telangana", "Karnataka", "TamilNadu")
        val childDataData1: MutableList<ChildData> = mutableListOf(ChildData("Anathapur"),ChildData("Chittoor"),ChildData("Nellore"),ChildData("Guntur"))
        val childDataData2: MutableList<ChildData> = mutableListOf(ChildData("Rajanna Sircilla"), ChildData("Karimnagar"), ChildData("Siddipet"))
        val childDataData3: MutableList<ChildData> = mutableListOf(ChildData("Chennai"), ChildData("Erode"))

        val parentObj1 = CategoryParentModel(parentTitle = parentData[0], subList = childDataData1)
        val parentObj2 = CategoryParentModel(parentTitle = parentData[1], subList = childDataData2)
        val parentObj3 = CategoryParentModel(parentTitle = parentData[2])
        val parentObj4 = CategoryParentModel(parentTitle = parentData[1], subList = childDataData3)

        listData.add(parentObj1)
        listData.add(parentObj2)
        listData.add(parentObj3)
        listData.add(parentObj4)

        val adapter = RecycleAdapter(this,listData)
        categoryRecyclerView.adapter = adapter
        categoryRecyclerView.layoutManager = LinearLayoutManager(this)
    }
}
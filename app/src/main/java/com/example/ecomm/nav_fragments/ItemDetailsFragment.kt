package com.example.ecomm.nav_fragments

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.amazon.database.DBHelper
import com.example.ecomm.R
import kotlinx.android.synthetic.main.fragment_item_details.view.*
import kotlin.collections.ArrayList


class ItemDetailsFragment : Fragment() {

    private var id: String? =""
    private var title : String? =""
    private var thumbnail : String? =""
    private var brand : String? =""
    private var price : String? =""
    private var listOfFavoritePhrases = ArrayList<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    @SuppressLint("Range")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val intent = requireActivity().intent
//        var title = requireArguments().getString("title")
        val thisContext = container!!.context
        var view = inflater.inflate(R.layout.fragment_item_details, container, false)
        var button2 = view.findViewById<Button>(R.id.button)


//        if (intent.extras != null) {
//            this.title = intent.getStringExtra("title").toString()
//            print(title)
//        }

        title = arguments?.getString("title")
        thumbnail = arguments?.getString("thumbnail")
        brand = arguments?.getString("brand")
        price = arguments?.getString("price")

        print(title)
        print(thumbnail)

        var imageView = view.findViewById<ImageView>(R.id.image_view)
        imageView.clipToOutline = true
        Glide.with(requireContext()).load(thumbnail).into(imageView)
        view.productTitleView.text = title.toString()
        view.productBrandView.text = brand.toString()
        view.productPriceView.text = "$${ price.toString() }"
        view.layio.visibility = View.INVISIBLE
        var quantity = 0
        view.toolbar.setNavigationOnClickListener { requireActivity().onBackPressed() }
        view.backButton.setOnClickListener {
            requireActivity()
                .supportFragmentManager
                .beginTransaction()
                .replace(R.id.mainContainer, HomeFragment())
                .commitNow()
        }
        button2.setOnClickListener {
            quantity++
            view.quantityNumber.text = quantity.toString()
            button2.visibility = View.INVISIBLE
            view.layio.visibility = View.VISIBLE
            requireActivity().getSharedPreferences("shopping_cart", Context.MODE_PRIVATE).edit()
                .apply {
                    putString("cart_mount", "twenty dollars")
                    putString("cart_tax", "twenty dollars")
                    putString("cart_quantity", "twenty dollars")
                    putString("cart_latest_item", title)
                }.apply()

            val dummy = setOf<String>("dummy")
            val prefs: SharedPreferences =
                requireActivity()!!.getSharedPreferences("PACKAGE", Context.MODE_PRIVATE)
//            val serialized = prefs.getStringSet("phrases", dummy)
            var data = prefs.getString("phrases", "")
            var appendedValue = data
            if (data == "") {
                appendedValue = data.plus(title)
            } else {
                appendedValue = data.plus(",$title")
            }
//            listOfFavoritePhrases =
//                ArrayList<String>(listOf(TextUtils.split(serialized, ",").toString()))

            var set = mutableSetOf<String>()
//            if (serialized != null) {
//                set = serialized
//            }

            set!!.add(title.toString())
            listOfFavoritePhrases.add(title.toString());

//            val prefs2: SharedPreferences = requireActivity().getSharedPreferences("PACKAGE", Context.MODE_PRIVATE)
            val editor = prefs.edit()
            editor.clear()
            editor.putString("phrases", appendedValue)
            editor.commit()

            val db = DBHelper(thisContext, null)

//            val cursor = db.getName()
//            cursor!!.moveToFirst()
//            var cartTitleList = arrayListOf<String>()
//            cartTitleList.add(cursor.getString(cursor.getColumnIndex(DBHelper.NAME_COl)))
//            while (cursor.moveToNext()) {
//                cartTitleList.add(cursor.getString(cursor.getColumnIndex(DBHelper.NAME_COl)))
//            }
//            id= (cartTitleList.size+1).toString()

            // creating variables for values
            // in name and age edit texts
            val name = title
            val age = price
            val quantity = 1
            // calling method to add
            // name to our database
            db.addName(name!!,thumbnail!!, age!!,quantity.toString())

            // Toast to message on the screen
            Toast.makeText(thisContext, name + " added to cart", Toast.LENGTH_LONG).show()



        }

        val db = DBHelper(thisContext, null)
        // below is the variable for cursor
        // we have called method to get
        // all names from our database
        // and add to name text view
        val cursor = db.getName()

        // moving the cursor to first position and
        // appending value in the text view
        cursor!!.moveToFirst()
        if(cursor.count>0){
            var cartTitleList = arrayListOf<String>()
            var cartQuantityList = arrayListOf<String>()

            cartTitleList.add(cursor.getString(cursor.getColumnIndex(DBHelper.TITLE_COL)))
            cartQuantityList.add(cursor.getString(cursor.getColumnIndex(DBHelper.QUANTITY_COL)))
            while (cursor.moveToNext()) {
                cartTitleList.add(cursor.getString(cursor.getColumnIndex(DBHelper.TITLE_COL)))
                cartQuantityList.add(cursor.getString(cursor.getColumnIndex(DBHelper.QUANTITY_COL)))
            }

            for (i in 0 until cartTitleList.size) {
                if (cartTitleList[i] == title) {
                    quantity = cartQuantityList[i].toInt()
                    view.quantityNumber.text = quantity.toString()
                    button2.visibility = View.INVISIBLE
                    view.layio.visibility = View.VISIBLE
                }
            }
            view.more.setOnClickListener {
                db.addQuantity(cartTitleList,thumbnail!!, title!!, price!!, quantity.toString())
                quantity++
                view.quantityNumber.text = quantity.toString()
            }
            view.less.setOnClickListener {
                db.onDelete(cartTitleList, title!!)
                quantity--
                view.quantityNumber.text = quantity.toString()
                if (quantity <= 0) {
                    button2.visibility = View.VISIBLE
                    view.layio.visibility = View.INVISIBLE
                }
            }
        }


        return view
    }
}
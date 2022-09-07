package com.example.ecomm.nav_fragments

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ecomm.OrderApiService
import com.example.ecomm.database.DBHelper
import com.example.ecomm.R
import com.example.ecomm.`interface`.MyCallBack
import com.example.ecomm.adapters.CartAdapter
import com.example.ecomm.models.CartModel
import com.example.ecomm.models.OrderModel
import com.example.ecomm.models.ProductDetail
import kotlinx.android.synthetic.main.fragment_cart.view.*
import kotlin.collections.ArrayList


class CartFragment : Fragment(), MyCallBack {

    private lateinit var cartTitleList : ArrayList<String>
    private lateinit var cartPriceList : ArrayList<String>
    private lateinit var cartCategoryList : ArrayList<String>
    private lateinit var cartQuantityList : ArrayList<String>
    private lateinit var cartImageList : ArrayList<String>
    lateinit var adapter: CartAdapter
//    lateinit var product: ArrayList<CartModel>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    @SuppressLint("Range")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_cart, container, false)
        val thisContext = container!!.context
        val textViewText = requireActivity()!!.getSharedPreferences("shopping_cart", Context.MODE_PRIVATE)
            .getString("cart_latest_item", "default value")
//        view.cartTextView.text = textViewText

        val preferences: SharedPreferences =
            requireActivity().getSharedPreferences("myPrefs", AppCompatActivity.MODE_PRIVATE)
        var username = preferences.getString("username", "")
        var totalPrice = 0

//        printName.setOnClickListener {

            // creating a DBHelper class
            // and passing context to it
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
                cartTitleList = arrayListOf<String>()
                cartPriceList = arrayListOf<String>()
                cartQuantityList = arrayListOf<String>()
                cartImageList = arrayListOf<String>()
                cartCategoryList = arrayListOf<String>()
                cartTitleList.add(cursor.getString(cursor.getColumnIndex(DBHelper.TITLE_COL)))
                cartImageList.add(cursor.getString(cursor.getColumnIndex(DBHelper.IMAGE_COL)))
                cartPriceList.add(cursor.getString(cursor.getColumnIndex(DBHelper.PRICE_COL)))
                cartCategoryList.add(cursor.getString(cursor.getColumnIndex(DBHelper.CATEGORY_COL)))
                cartQuantityList.add(cursor.getString(cursor.getColumnIndex(DBHelper.QUANTITY_COL)))
//        print("ajcajcds")
//        print("cart list: $cartTitleList")

//        var cartTextView = view.findViewById<TextView>(R.id.cartTitleView)
//            cartTextView.append(cursor.getString(cursor.getColumnIndex(DBHelper.NAME_COl)) + "\n")
//            view.cartPriceView.append(cursor.getString(cursor.getColumnIndex(DBHelper.AGE_COL)) + "\n")

                // moving our cursor to next
                // position and appending values
                while (cursor.moveToNext()) {
                    cartTitleList.add(cursor.getString(cursor.getColumnIndex(DBHelper.TITLE_COL)))
                    cartImageList.add(cursor.getString(cursor.getColumnIndex(DBHelper.IMAGE_COL)))
                    cartPriceList.add(cursor.getString(cursor.getColumnIndex(DBHelper.PRICE_COL)))
                    cartCategoryList.add(cursor.getString(cursor.getColumnIndex(DBHelper.CATEGORY_COL)))
                    cartQuantityList.add(cursor.getString(cursor.getColumnIndex(DBHelper.QUANTITY_COL)))
//                cartTextView.append(cursor.getString(cursor.getColumnIndex(DBHelper.NAME_COl)) + "\n")
//                view.cartPriceView.append(cursor.getString(cursor.getColumnIndex(DBHelper.AGE_COL)) + "\n")

                }
                println(cartTitleList.groupingBy { it }.eachCount().filter { it.value > 1 })
                val product = ArrayList<CartModel>()
//        product.add(CartModel("abcd"))
                for (i in 0..(cartTitleList.size - 1)) {
//                var quantity = Collections.frequency(cartTitleList, item)

                    totalPrice =
                        totalPrice + (cartPriceList[i].toInt() * cartQuantityList[i].toInt())

                    product.add(
                        CartModel(
                            cartTitleList[i],
                            cartImageList[i],
                            cartCategoryList[i],
                            cartQuantityList[i].toInt(),
                            cartPriceList[i],
                            totalPrice
                        )
                    )
//            cartTextView.append(item + ": " + Collections.frequency(cartTitleList, item) + "\n")
                }
                Log.d("Print", cartTitleList.toString())

                // at last we close our cursor
                cursor.close()
//        view.deleteButton.setOnClickListener {
//            db.onDelete("iPhone X")
//        }
                adapter = CartAdapter(thisContext, product, view)
                view.cartRecyclerView.adapter = adapter
                view.cartTotal.text = "Subtotal: $$totalPrice"
                view.cartRecyclerView.layoutManager = LinearLayoutManager(thisContext)
            }
        else{
            view.cartTotal.text=="$0"
            }
        view.checkoutButton.setOnClickListener {
            var quantityList = arrayListOf<Int>()
            var priceList = arrayListOf<Int>()
            var order = arrayListOf<ProductDetail>()
            for (i in 0..(cartTitleList.size - 1)){

                quantityList.add(cartQuantityList[i].toInt())
                priceList.add(cartPriceList[i].toInt())
                order.add(ProductDetail(cartTitleList[i],cartCategoryList[i],cartQuantityList[i].toInt(),cartPriceList[i].toInt(),totalPrice))
            }
            addDummyUser(username!!,order)
        }

        return view
    }

    fun setCartTotal(view: View, totalPrice: String){
        view.cartTotal.text = "Subtotal: $$totalPrice"
    }

    override fun updateMyText(myString: String?) {
        requireActivity().findViewById<TextView>(R.id.cartTotal).text = myString
    }

    fun addDummyUser(
        uid:String, order:ArrayList<ProductDetail>) {
        val apiService = OrderApiService()
        val userInfo = OrderModel(
            uid,order
        )
            apiService.addUser(userInfo) {
                if (it?.uid != null) {
                    Log.e("", it.toString())
                    Toast.makeText(requireContext(),"Order Created Successfully", Toast.LENGTH_SHORT).show()
                    // it = newly added user parsed as response
                    // it?.id = newly added user ID
                } else {
                    Toast.makeText(requireContext(),"Some Error Occurred", Toast.LENGTH_SHORT).show()
                }
            }
        }

    fun Array<String>.asInts() = this.map { it.toInt() }.toTypedArray()
}

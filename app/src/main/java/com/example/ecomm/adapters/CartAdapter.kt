package com.example.ecomm.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.ecomm.R
import com.example.ecomm.`interface`.MyCallBack
import com.example.ecomm.database.DBHelper
import com.example.ecomm.models.CartModel
import com.example.ecomm.nav_fragments.CartFragment


class CartAdapter(val context: Context, val articles: List<CartModel>, val view:View) : RecyclerView.Adapter<CartAdapter.CartViewHolder>(){
    var myCallback: MyCallBack? = null
    var total=0
    fun CartAdapter(callback: MyCallBack) {
        this.myCallback = callback
    }


    class CartViewHolder(itemView: View,itemView2: View) : RecyclerView.ViewHolder(itemView){
        var productImage = itemView.findViewById<ImageView>(R.id.productImage)
        var cartItem = itemView.findViewById<LinearLayout>(R.id.cartItem)
        var productName = itemView.findViewById<TextView>(R.id.productName)
        var productQuantity = itemView.findViewById<TextView>(R.id.prnumber)
        var productPrice = itemView.findViewById<TextView>(R.id.productPrice)
        var increaseButton = itemView.findViewById<CardView>(R.id.more)
        var decreaseButton = itemView.findViewById<CardView>(R.id.less)
        var cartTotal = itemView2.findViewById<TextView>(R.id.cartTotal)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.cart_layout, parent, false)
        val view2 = LayoutInflater.from(context).inflate(R.layout.fragment_cart, parent, false)
        return CartViewHolder(view,view2)
    }

    @SuppressLint("Range")
    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        var cartList = ArrayList<String>()
        for(element in articles){
                cartList.add(element.title)
        }
        val article = articles[position]
        var quantity = article.quantity
        holder.productName.text = article.title
        holder.productQuantity.text =article.quantity.toString()
        holder.productPrice.text = "$${article.price.toInt() * article.quantity}"
//        Glide.with(context).load(article.image).into(holder.productImage)
        total = article.totalPrice
        holder.increaseButton.setOnClickListener {
            total+=article.price.toInt()
            val db = DBHelper(context, null)
            val name = article.title
            val age = article.price

//            val cursor = db.getName()
//            cursor!!.moveToFirst()
//            var cartTitleList = arrayListOf<String>()
//            cartTitleList.add(cursor.getString(cursor.getColumnIndex(DBHelper.NAME_COl)))
//            while (cursor.moveToNext()) {
//                cartTitleList.add(cursor.getString(cursor.getColumnIndex(DBHelper.NAME_COl)))
//            }
//            var id = (cartTitleList.size + 1).toString()

            db.addQuantity(cartList,article.image,"megha@yadav", article.category, article.title, article.price, article.quantity.toString())
            quantity++
            holder.productQuantity.text = quantity.toString()
            holder.productPrice.text = "$${article.price.toInt() * quantity}"
            var cartFragment = CartFragment()
            cartFragment.setCartTotal(view, "${total}")
            Log.e("Updated", article.totalPrice.toString())
//            holder.cartTotal.text = "Subtotal: $${article.totalPrice+article.price.toInt()}"
            Log.e("", holder.cartTotal.text as String)
//            notifyDataSetChanged()
        }
        holder.decreaseButton.setOnClickListener {
            total-=article.price.toInt()
            val db = DBHelper(context, null)
            val name = article.title
            val age = article.price

            // calling method to add
            // name to our database

            Log.e("", "I am running")
            quantity--
            holder.productQuantity.text = quantity.toString()
            Log.e("", "I have runjcsbj")
            holder.productPrice.text = "$${article.price.toInt() * quantity}"
            db.onDelete(cartList, article.title)
            var cartFragment = CartFragment()
            cartFragment.setCartTotal(view, "${total}")
            holder.cartTotal.text = "${article.totalPrice-article.price.toInt()}"
            if(quantity==0){
                Log.e("", "poof")
                holder.cartItem.visibility=View.INVISIBLE
            }else{
                Log.e("", "poof")
                Log.e("", quantity.toString())
                holder.cartItem.visibility=View.VISIBLE
            }
//            notifyDataSetChanged()
        }

//        holder.productPrice.text = "$${articles.price}"
//        holder.productPrice.showStrikeThrough(true)
//        Glide.with(context).load(articles.thumbnail).into(holder.productImage)
    }

    override fun getItemCount(): Int {
        return articles.size
    }
}
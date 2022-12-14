package com.example.ecomm.adapters

import android.content.Context
import android.content.SharedPreferences
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.transition.AutoTransition
import android.transition.TransitionManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.ecomm.AddAddressApiService
import com.example.ecomm.DeleteAddressApiService
import com.example.ecomm.EditAddressApiService
import com.example.ecomm.R
import com.example.ecomm.`interface`.DeleteAddressApi
import com.example.ecomm.models.AddAddress
import com.example.ecomm.models.AddressModel


class ExpandableCardAdapter(val context: Context, val articles: ArrayList<AddressModel>) : RecyclerView.Adapter<ExpandableCardAdapter.ExpandableCardView>() {

    private lateinit var mListener: onItemClickListener
    var flag = false

    interface onItemClickListener{

        fun onItemClick(position: Int)

    }

    fun setOnItemClickListener(listener: onItemClickListener){

        mListener = listener

    }

    class ExpandableCardView(itemView: View, listener: onItemClickListener) : RecyclerView.ViewHolder(itemView){

        var name = itemView.findViewById<TextView>(R.id.name)
        var phone = itemView.findViewById<TextView>(R.id.phone)
        var address = itemView.findViewById<TextView>(R.id.address)
        var city = itemView.findViewById<TextView>(R.id.city)
        var state = itemView.findViewById<TextView>(R.id.state)
        var country = itemView.findViewById<TextView>(R.id.country)
        var expandBtn = itemView.findViewById<Button>(R.id.expandBtn)
        var editBtn = itemView.findViewById<ImageButton>(R.id.editBtn)
        var deleteBtn = itemView.findViewById<ImageButton>(R.id.deleteBtn)
        var expandableCard = itemView.findViewById<LinearLayout>(R.id.expandableLayout)
        var cardView = itemView.findViewById<CardView>(R.id.cardView)
        init {
            itemView.setOnClickListener {
                listener.onItemClick(adapterPosition)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExpandableCardView {
        val view = LayoutInflater.from(context).inflate(R.layout.address_card_layout, parent, false)
        return ExpandableCardView(view, mListener)
    }

    override fun onBindViewHolder(holder: ExpandableCardView, position: Int) {
        val article = articles[position]

        val preferences: SharedPreferences =
            context.getSharedPreferences("myPrefs", AppCompatActivity.MODE_PRIVATE)
        var username = preferences.getString("username", "")

        holder.name.text = article.name
        holder.phone.text = article.phone
        holder.address.text = article.address
        holder.city.text = article.city
        holder.state.text = article.state
        holder.country.text = article.country
        holder.expandBtn.setOnClickListener {
            var vibrator = context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            if (vibrator.hasVibrator()) { // Vibrator availability checking
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                        vibrator.vibrate(VibrationEffect.createPredefined(VibrationEffect.EFFECT_CLICK))
                    } // New vibrate method for API Level 26 or higher
                } else {
                    vibrator.vibrate(500) // Vibrate method for below API Level 26
                }
            }
                if (holder.expandableCard.visibility == View.GONE) {
                    TransitionManager.beginDelayedTransition(holder.cardView, AutoTransition())

                    holder.expandableCard.visibility = View.VISIBLE
                    holder.expandBtn.text = "Less"
                } else {
                    holder.expandableCard.visibility = View.GONE
                    holder.expandBtn.text = "More"
                    holder.editBtn.setImageResource(R.drawable.edit)
                }
            }

        holder.editBtn.setOnClickListener {
            flag = if(!flag){
                holder.editBtn.setImageResource(R.drawable.tick)
                TransitionManager.beginDelayedTransition(holder.cardView, AutoTransition())
                holder.expandableCard.visibility = View.VISIBLE
                holder.expandBtn.text = "Less"

                holder.name.isEnabled = true
                holder.phone.isEnabled = true
                holder.address.isEnabled = true
                holder.city.isEnabled = true
                holder.state.isEnabled = true
                holder.country.isEnabled = true
                true
            } else{
//                val repository = Repository()
//                runBlocking {
//                    launch {
//                        val response = repository.getPost(article.id)
//                        Log.e("jbsacba",response.body().toString())
//                    }
//                }
                editDummyAddress(article.id,username!!,holder.address.text.toString(),holder.city.text.toString(),holder.state.text.toString(),holder.country.text.toString())
                holder.editBtn.setImageResource(R.drawable.edit)
                holder.name.isEnabled = false
                holder.phone.isEnabled = false
                holder.address.isEnabled = false
                holder.city.isEnabled = false
                holder.state.isEnabled = false
                holder.country.isEnabled = false
                false
            }
        }
        holder.deleteBtn.setOnClickListener {

//            articles.removeAt(position)
            deleteDummyAddress(article.id)
            Toast.makeText(context,"Your address has been removed", Toast.LENGTH_SHORT).show()
            notifyItemRemoved(position)
        }

        }

    override fun getItemCount(): Int {
        return articles.size
    }

    fun editDummyAddress(
        id:String,
        username: String,
        address: String,
        city: String,
        state: String,
        country: String
    ) {

        val apiService = EditAddressApiService()
        var userInfo = AddAddress(
            username, address, city, state, country
        )

        apiService.addUser(id,userInfo) {
            if (it?.address != null) {
                Log.e("axjbakxn", it.toString())
//                view.product.add(
//                    AddressModel(
//                        it._id.toString(),
//                        "Megha",
//                        "0987654321",
//                        it.address.toString(),
//                        it.city.toString(),
//                        it.state.toString(),
//                        it.country.toString(),
//                    )
//                )
            } else {
                Log.e("", "Error registering new user")
            }
        }
    }
    fun deleteDummyAddress(
        id:String
    ) {

        val apiService = DeleteAddressApiService()

        apiService.addUser(id) {
            if (it?.acknowledged != null) {
                Log.e("axjbakxn", it.toString())
            } else {
                Log.e("", "Error registering new user")
            }
        }
    }

}
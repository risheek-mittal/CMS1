package com.example.ecomm.nav_fragments

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.os.Bundle
import android.transition.AutoTransition
import android.transition.TransitionManager
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ecomm.AddAddressApiService
import com.example.ecomm.AddressApiService
import com.example.ecomm.R
import com.example.ecomm.RecycleAdapter
import com.example.ecomm.`interface`.ModelApi
import com.example.ecomm.adapters.ExpandableCardAdapter
import com.example.ecomm.adapters.ProductAdapter
import com.example.ecomm.models.AddAddress
import com.example.ecomm.models.AddressModel
import kotlinx.android.synthetic.main.fragment_address.view.*
import kotlinx.android.synthetic.main.fragment_tab_home.*
import kotlinx.android.synthetic.main.fragment_tab_home.view.*
import kotlinx.android.synthetic.main.fragment_tab_home.view.productList
import kotlinx.android.synthetic.main.fragment_tab_home.view.swipeRefresh

class AddressFragment : Fragment() {

    lateinit var adapter: ExpandableCardAdapter
    lateinit var product: ArrayList<AddressModel>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(com.example.ecomm.R.layout.fragment_address, container, false)
        var thisContext = requireActivity().applicationContext
        val preferences: SharedPreferences =
            requireActivity().getSharedPreferences("myPrefs", MODE_PRIVATE)
        var username = preferences.getString("username", "")
        view.swipeRefresh.setOnRefreshListener {
            view.swipeRefresh.isRefreshing = false
            refreshFragments()
        }
        showDummyAddress(thisContext, view)
        product = arrayListOf()
        Log.e("", product.size.toString())

        view.addAddressBtn.setOnClickListener {
            if (view.cardView.visibility == View.GONE) {
                TransitionManager.beginDelayedTransition(view.cardView, AutoTransition())

                view.cardView.visibility = View.VISIBLE
            } else {
                view.cardView.visibility = View.GONE
            }
        }

        view.editBtn.setOnClickListener {
            addDummyAddress(
                thisContext,
                view,
                username!!,
                view.address.text.toString(),
                view.city.text.toString(),
                view.state.text.toString(),
                view.country.text.toString()
            )
            refreshFragments()
            Toast.makeText(thisContext, "Your address is successfully added", Toast.LENGTH_SHORT)
                .show()
            view.cardView.visibility = View.GONE
        }
        return view
    }

    fun refreshFragments() {
        productList.adapter = adapter
    }

    fun showDummyAddress(thisContext: Context, view: View) {

        val apiService = AddressApiService()

        apiService.addUser() {
            println(it?.first())
            if (it?.first()!!.address != null) {
                Log.e("axjbakxn", it.toString())
                for (i in it) {
                    product.add(
                        AddressModel(
                            i._id.toString(),
                            i.addedBy!!.fname.toString(),
                            i.addedBy!!.phone.toString(),
                            i.address.toString(),
                            i.city.toString(),
                            i.state.toString(),
                            i.country.toString(),
                        )
                    )
                }
                adapter = ExpandableCardAdapter(thisContext, product)
                view.productList.layoutManager = LinearLayoutManager(thisContext)
                view.productList.adapter = adapter
                adapter.setOnItemClickListener(object : ExpandableCardAdapter.onItemClickListener {
                    override fun onItemClick(position: Int) {
                        println("click")
                    }

                })
            } else {
                Log.e("", "Error registering new user")
            }
        }
    }

    fun addDummyAddress(
        thisContext: Context,
        view: View,
        username: String,
        address: String,
        city: String,
        state: String,
        country: String
    ) {

        val apiService = AddAddressApiService()
        var userInfo = AddAddress(
            username, address, city, state, country
        )

        apiService.addUser(userInfo) {
            if (it?.address != null) {
                Log.e("axjbakxn", it.toString())
                product.add(
                    AddressModel(
                        it._id.toString(),
                        "Megha",
                        "0987654321",
                        it.address.toString(),
                        it.city.toString(),
                        it.state.toString(),
                        it.country.toString(),
                    )
                )
            } else {
                Log.e("", "Error registering new user")
            }
        }
    }


}
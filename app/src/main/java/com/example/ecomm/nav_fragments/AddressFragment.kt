package com.example.ecomm.nav_fragments

import android.content.Context
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
import com.example.ecomm.R
import com.example.ecomm.RecycleAdapter
import com.example.ecomm.`interface`.ModelApi
import com.example.ecomm.adapters.ExpandableCardAdapter
import com.example.ecomm.adapters.ProductAdapter
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
        view.swipeRefresh.setOnRefreshListener {
            view.swipeRefresh.isRefreshing = false
            refreshFragments()
        }
        product = arrayListOf(
            AddressModel(
                "Risheek",
                "9373823242",
                "AdGlobal360",
                "Gurgaon",
                "Haryana",
                "India"
            )
        )
        product.add(
            AddressModel(
                "Mittal",
                "9373823242",
                "AdGlobal360",
                "Gurgaon",
                "Haryana",
                "India"
            )
        )
        product.add(
            AddressModel(
                "Risheek",
                "9373823242",
                "AdGlobal360",
                "Gurgaon",
                "Haryana",
                "India"
            )
        )
        Log.e("",product.size.toString())
        adapter = ExpandableCardAdapter(thisContext, product)
        view.productList.layoutManager = LinearLayoutManager(thisContext)
        view.productList.adapter = adapter
        adapter.setOnItemClickListener(object : ExpandableCardAdapter.onItemClickListener {
            override fun onItemClick(position: Int) {
                println("click")
            }

        })
       view.addAddressBtn.setOnClickListener {
           if (view.cardView.visibility == View.GONE) {
               TransitionManager.beginDelayedTransition(view.cardView, AutoTransition())

               view.cardView.visibility = View.VISIBLE
           } else {
               view.cardView.visibility = View.GONE
           }
        }

        view.editBtn.setOnClickListener {
            product.add(
                AddressModel(
                    "Abhishek",
                    "9373823242",
                    "AdGlobal360",
                    "Gurgaon",
                    "Haryana",
                    "India"
                )
            )
            refreshFragments()
            Toast.makeText(thisContext,"Your address is successfully added",Toast.LENGTH_SHORT)
            view.cardView.visibility = View.GONE
        }
        return view
    }

    fun refreshFragments() {
        productList.adapter = adapter
    }

}
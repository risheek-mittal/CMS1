package com.example.ecomm.tab_fragment

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ecomm.nav_fragments.ItemDetailsFragment
import com.example.ecomm.R
import com.example.ecomm.`interface`.ModelApi
import com.example.ecomm.adapters.ProductAdapter
import com.example.ecomm.models.ApiModel
import com.example.ecomm.models.Retro
import com.example.ecomm.objects.Communicator
import kotlinx.android.synthetic.main.fragment_tab_home.*
import kotlinx.android.synthetic.main.fragment_tab_home.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class TabHomeFragment : Fragment() {

    lateinit var adapter: ProductAdapter
    lateinit var product: List<ApiModel>
    lateinit var subProduct: List<ApiModel>
    lateinit var communicator: Communicator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(com.example.ecomm.R.layout.fragment_tab_home, container, false)
        var thisContext = requireActivity().applicationContext
        communicator = activity as Communicator
        getQuote(view,thisContext,communicator)
        view.swipeRefresh.setOnRefreshListener {
            view.swipeRefresh.isRefreshing = false
            refreshFragments()
        }
        return view
    }

    private fun getQuote(view: View, context: Context, communicator: Communicator) {
        val retro = Retro().getRetroClient().create(ModelApi::class.java)
        retro.getQuote().enqueue(object : Callback<List<ApiModel>> {
            override fun onResponse(
                call: Call<List<ApiModel>>,
                response: Response<List<ApiModel>>
            ) {
                Log.d("Return", response.body()!!.toString())
                for (q in response.body()!!) {
                    Log.e("Wow", q.name.toString())
                }
                product = response!!.body()!!
                Log.d("Rewsponse", response!!.body().toString())
                subProduct = listOf()
//                val list = response.
                for(element in product){
                    var article = element
                    println(article.category)
                    if(article.category==requireArguments().getString("position")){
                        Log.e("","start")
                        subProduct = subProduct.plus(article)
                        Log.e("suproductaddd", subProduct.size.toString())
                        Log.e("","end")
                    }
                }
                Log.e("suproduct", subProduct.size.toString())
                adapter = ProductAdapter(context, subProduct)
                view.productList.layoutManager = LinearLayoutManager(context)
                view.productList.adapter = adapter
                adapter.setOnItemClickListener(object : ProductAdapter.onItemClickListener {
                    override fun onItemClick(position: Int) {
                        communicator.passDataCom(
                            subProduct[position].name.toString(),
                            subProduct[position].category.toString(),
                            product[position].thumbnail.toString(),
                            product[position].brand.toString(),
                            subProduct[position].price.toString()
                        )
//                        val intent = Intent(requireActivity(), ItemDetailsFragment::class.java)
//                        requireActivity().startActivity(intent)
//                        requireActivity().run{
//                            startActivity(Intent(this, ItemDetailsFragment::class.java))
//                            finish()
                        var bundle = Bundle()
                        bundle.putString("title", product[position].name)
                        val itemDetailsFragment = ItemDetailsFragment()
//                        itemDetailsFragment.requireArguments().putString("title",product[position].title)
                        activity!!
                            .supportFragmentManager
                            .beginTransaction()
                            .replace(R.id.mainContainer, ItemDetailsFragment())
                            .commitNow()
//                        }
                    }

                })
            }

            override fun onFailure(call: Call<List<ApiModel>>, t: Throwable) {
                Log.e("Fail", t.toString())
            }

        })
    }

    fun refreshFragments() {
        productList.adapter = adapter
    }


//    private fun initViews(view: View) {
//        val textView: TextView = view.findViewById(com.example.ecomm.R.id.commonTextView)
//        textView.text = "Category :  " + requireArguments().getInt("position")
//    }
}
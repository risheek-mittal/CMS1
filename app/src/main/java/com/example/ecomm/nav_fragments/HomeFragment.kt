package com.example.ecomm.nav_fragments


import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.view.ViewCompat
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.example.ecomm.R
import com.example.ecomm.adapters.DynamicFragmentAdapter
import com.example.ecomm.models.ApiModel
import com.example.ecomm.tab_fragment.TabHomeFragment
import com.google.android.material.tabs.TabLayout


class HomeFragment : Fragment() {
    private var viewPager: ViewPager? = null
    private var mTabLayout: TabLayout? = null
    lateinit var product: List<ApiModel>
    private var isDataLoaded : Boolean = false
    private var count = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
//        (activity as AppCompatActivity).setSupportActionBar(toolbar_name)
        (activity as AppCompatActivity).supportActionBar?.title = "Home"
//        imageArray.add(R.drawable.one)
//        imageArray.add(R.drawable.two)
//        imageArray.add(R.drawable.three)
//        imageArray.add(R.drawable.four)
        requireActivity().setTitle("Home")
        val view = inflater.inflate(R.layout.fragment_home2, container, false)
        initViews(view)
        return view
    }

    private fun initViews(view:View) {
        viewPager = view.findViewById(R.id.viewpager)
        mTabLayout = view.findViewById(R.id.tabs)
        viewPager!!.offscreenPageLimit = 5
        viewPager!!.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(mTabLayout))
        mTabLayout!!.setOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                // setCurrentItem as the tab position
                viewPager!!.currentItem = tab.position
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {}
            override fun onTabReselected(tab: TabLayout.Tab) {
                viewPager!!.currentItem = tab.position
            }
        })
        setDynamicFragmentToTabLayout()
    }

    fun setDynamicFragmentToTabLayout() {
        var category = arrayListOf<String>("Shoes","clothes", "laptop", "fragrances", "skincare", "groceries")
        for (i in 0..4) {
//            val retro = Retro().getRetroClient().create(ModelApi::class.java)
//            retro.getQuote().enqueue(object : Callback<List<ApiModel>> {
//                override fun onResponse(
//                    call: Call<List<ApiModel>>,
//                    response: Response<List<ApiModel>>
//                ) {
////                    Log.d("Return", response.body()!!.toString())
////                    for (q in response.body()!!) {
////                        Log.e("Wow", q.title.toString())
////                    }
//                    product = response!!.body()!!
//                    var article = product[i]
//                    category.add(article.category.toString())
//                    println(article.category)
//                    Log.e("cat", article.category.toString())
//                    mTabLayout!!.addTab(mTabLayout!!.newTab().setText(article.category))
//                }
//
//                override fun onFailure(call: Call<List<ApiModel>>, t: Throwable) {
//                    Log.e("Fail", t.toString())
//                }
//            })
            mTabLayout!!.addTab(mTabLayout!!.newTab().setText("${category[i]}"))
        }
        val tabItem = mTabLayout
        for (i in 0 until category.size) {
            val tabView = tabItem!!.getChildAt(i)
            if (tabView != null && i == 1) {
                val paddingStart = tabView.paddingStart
                val paddingTop = tabView.paddingTop
                val paddingEnd = tabView.paddingEnd
                val paddingBottom = tabView.paddingBottom
                ViewCompat.setBackground(
                    tabView,
                    AppCompatResources.getDrawable(
                        requireContext(),
                        R.drawable.circular
                    )
                )
                ViewCompat.setPaddingRelative(
                    tabView,
                    paddingStart,
                    paddingTop,
                    paddingEnd,
                    paddingBottom
                )
            }
        }

            Log.e("", "printeddd")
            val mDynamicFragmentAdapter = DynamicFragmentAdapter(
                childFragmentManager, mTabLayout!!.tabCount, category
            )
        Log.e("", "printeddd")
            viewPager!!.adapter = mDynamicFragmentAdapter
        if(count!=0) {
            Log.e("","runnn")
//            TabHomeFragment().refreshFragments()
        }
        Log.e("", "pri")
        viewPager!!.adapter!!.notifyDataSetChanged()
            // set the current item as 0 (when app opens for first time)

            // set the current item as 0 (when app opens for first time)
            viewPager!!.currentItem = 0
        isDataLoaded=true
        count++
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        if (isVisibleToUser) {

            // Refresh tab data:
            fragmentManager?.beginTransaction()?.detach(this)?.attach(this)?.commit()
        }
    }

}
package com.example.ecomm.adapters

import android.os.Bundle
import android.provider.Settings.Global.putInt
import android.provider.Settings.Global.putString
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager

import androidx.fragment.app.FragmentStatePagerAdapter
import com.example.ecomm.tab_fragment.TabHomeFragment
import java.util.ArrayList
import javax.xml.parsers.SAXParserFactory.newInstance
import javax.xml.xpath.XPathFactory.newInstance


class DynamicFragmentAdapter internal constructor(
    fm: FragmentManager?,
    private val mNumOfTabs: Int,
    private val mCategory: ArrayList<String>
)  :
    FragmentStatePagerAdapter(fm!!) {
    // get the current item with position number
    override fun getItem(position: Int): Fragment {
        val b = Bundle()
        b.putString("position", mCategory[position])
        Log.e("","working")
        val frag: Fragment = TabHomeFragment()
        frag.arguments = b
        Log.e("","working22")
        return frag
    }

    // get total number of tabs
    override fun getCount(): Int {
        return mNumOfTabs
    }
}
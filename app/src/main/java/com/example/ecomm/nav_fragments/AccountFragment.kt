package com.example.ecomm.nav_fragments

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.ecomm.R
import com.github.dhaval2404.imagepicker.ImagePicker
import com.google.android.material.textfield.TextInputLayout
import kotlinx.android.synthetic.main.fragment_account.view.*
import kotlinx.android.synthetic.main.fragment_account.view.editBtn
import kotlinx.android.synthetic.main.fragment_account.view.name
import kotlinx.android.synthetic.main.fragment_address.view.*

class AccountFragment : Fragment() {

    var imagePicker: ImagePicker?=null
    var flag = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val bottomSheetFragment = BottomSheetFragment()
        val view = inflater.inflate(R.layout.fragment_account, container, false)
//        view.profileButton.setOnClickListener {
//            bottomSheetFragment.show(requireActivity().supportFragmentManager, "BottomSheetDialog")
//        }
        view.bg.clipToOutline = true
        view.accountFragment.clipChildren = false
        view.addressButton.clipToOutline = true
        view.profileButton.setOnClickListener {
            bottomSheetFragment.show(requireActivity().supportFragmentManager, "BottomSheetDialog")
        }
        disableClipOnParents(view)

        view.addressButton.setOnClickListener {
            requireActivity()
                .supportFragmentManager
                .beginTransaction()
                .replace(R.id.mainContainer, AddressFragment())
                .commitNow()
        }
            view.editBtn.setOnClickListener {
                flag = if (!flag) {
                    view.editBtn.setImageResource(R.drawable.tick)
                    view.name.isEnabled = true
                    view.email.isEnabled = true
                    true
                } else {
                    view.editBtn.setImageResource(R.drawable.edit)
                    view.name.isEnabled = false
                    view.email.isEnabled = false
                    false
                }
            }

        return view
    }
    fun disableClipOnParents(v: View) {
        if (v.parent == null) {
            return
        }
        if (v is ViewGroup) {
            v.clipChildren = false
        }
        if (v.parent is View) {
            disableClipOnParents(v.parent as View)
        }
    }
}
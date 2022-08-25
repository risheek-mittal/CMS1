package com.example.ecomm.nav_fragments

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.provider.SyncStateContract
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import com.example.ecomm.R
import com.github.dhaval2404.imagepicker.ImagePicker
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.bottom_sheet_fragment.view.*

class BottomSheetFragment : BottomSheetDialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.bottom_sheet_fragment, container, false)
//        val thisContext = container!!.context
        view.cameraButton.setOnClickListener {
//            Toast.makeText(thisContext, "You Pressed Camera", Toast.LENGTH_SHORT).show()
            ImagePicker.with(this).cameraOnly().start()
        }
        view.galleryButton.setOnClickListener {
//            Toast.makeText(thisContext, "You Pressed Gallery", Toast.LENGTH_SHORT).show()
            ImagePicker.with(this).galleryOnly().galleryMimeTypes(arrayOf("image/*"))
                .maxResultSize(400, 400).start()

        }
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


    }

//    var resultLauncher = requireActivity().registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
//        onActivityResult(SyncStateContract.Constants.MY_CODE_REQUEST, result)
//    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val profileButton = requireActivity().findViewById<ImageView>(R.id.profileButton)
        if(resultCode== Activity.RESULT_OK && requestCode==ImagePicker.REQUEST_CODE){
            profileButton.setImageURI(data?.data)
        }
    }

}
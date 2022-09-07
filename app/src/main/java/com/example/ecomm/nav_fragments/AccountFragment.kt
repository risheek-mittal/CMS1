package com.example.ecomm.nav_fragments

import android.Manifest
import android.app.Activity
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.net.wifi.WifiManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.example.ecomm.R
import com.github.dhaval2404.imagepicker.ImagePicker
import kotlinx.android.synthetic.main.fragment_account.*
import kotlinx.android.synthetic.main.fragment_account.view.*
import kotlinx.android.synthetic.main.fragment_account.view.editBtn
import kotlinx.android.synthetic.main.fragment_account.view.name
import kotlinx.android.synthetic.main.fragment_address.view.*


class AccountFragment : Fragment() {

    var imagePicker: ImagePicker?=null
    var flag = false
    lateinit var wifiManager: WifiManager

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

        wifiManager = requireActivity().applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager
        view.wifiSwitch.setOnCheckedChangeListener { _, isChecked ->
            checkPermission(Manifest.permission.CHANGE_WIFI_STATE,
                CAMERA_PERMISSION_CODE)
            if (isChecked) {
                wifiManager.isWifiEnabled = true
                view.wifiSwitch.text = "WiFi is ON"
            } else {
                wifiManager.isWifiEnabled = false
                view.wifiSwitch.text = "WiFi is OFF"
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

    override fun onStart() {
        super.onStart()
        val intentFilter = IntentFilter(WifiManager.WIFI_STATE_CHANGED_ACTION)
        LocalBroadcastManager.getInstance(requireActivity()).registerReceiver(wifiStateReceiver, intentFilter)
    }
    override fun onStop() {
        super.onStop()
        LocalBroadcastManager.getInstance(requireActivity()).unregisterReceiver(wifiStateReceiver)
    }

    private val wifiStateReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            when (intent.getIntExtra(WifiManager.EXTRA_WIFI_STATE,
                WifiManager.WIFI_STATE_UNKNOWN)) {
                WifiManager.WIFI_STATE_ENABLED -> {
                    wifiSwitch.isChecked = true
                    wifiSwitch.text = "WiFi is ON"
//                    Toast.makeText(this@MainActivity, "Wifi is On", Toast.LENGTH_SHORT).show()
                }
                WifiManager.WIFI_STATE_DISABLED -> {
                    wifiSwitch.isChecked = false
                    wifiSwitch.text = "WiFi is OFF"
//                    Toast.makeText(this@MainActivity, "Wifi is Off", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == CAMERA_PERMISSION_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(requireContext(), "Camera Permission Granted", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(requireContext(), "Camera Permission Denied", Toast.LENGTH_SHORT).show()
            }
        } else if (requestCode == STORAGE_PERMISSION_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(requireContext(), "Storage Permission Granted", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(requireContext(), "Storage Permission Denied", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun checkPermission(permission: String, requestCode: Int) {
        if (ContextCompat.checkSelfPermission(requireContext(), permission) == PackageManager.PERMISSION_DENIED) {
            // Requesting the permission
            ActivityCompat.requestPermissions(requireActivity(), arrayOf(permission), requestCode)
        } else {
            Toast.makeText(requireContext(), "Permission already granted", Toast.LENGTH_SHORT).show()
        }
    }

    companion object {
        private const val CAMERA_PERMISSION_CODE = 100
        private const val STORAGE_PERMISSION_CODE = 101
    }

}
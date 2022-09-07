package com.example.ecomm

import android.app.Activity
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Bundle
import android.widget.PopupMenu
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.example.ecomm.databinding.ActivityThirdHomeBinding
import com.example.ecomm.nav_fragments.ItemDetailsFragment
import com.example.ecomm.objects.Communicator
import kotlinx.android.synthetic.main.activity_third_home.*


class ThirdHomeActivity : AppCompatActivity(), Communicator {
    private lateinit var navController: NavController
    private lateinit var binding : ActivityThirdHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        val preferences: SharedPreferences =
            this.getSharedPreferences("myPrefs", MODE_PRIVATE)
        binding = ActivityThirdHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        navController = findNavController(R.id.mainContainer)
        window.setNavigationBarColor(Color.parseColor("#c69351"))
        window.statusBarColor = (Color.parseColor("#c69351"))
        setupSmoothBottomMenu()
        logoutButton.setOnClickListener {
            var editor = preferences.edit()
            editor.clear()
            editor.apply()
            val intent = Intent(this@ThirdHomeActivity, MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intent);
        }
//        val navHostFragment = supportFragmentManager.findFragmentById(R.id.mainContainer) as NavHostFragment
//        navController = navHostFragment.navController
//        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.nav_view)
//        NavigationUI.setupWithNavController(bottomNavigationView, navController)
    }

    override fun passDataCom(title: String, category:String, thumbnail: String, brand: String, price: String) {
        val bundle = Bundle()
        bundle.putString("title", title)
        bundle.putString("thumbnail", thumbnail)
        bundle.putString("brand", brand)
        bundle.putString("price", price)
        bundle.putString("category", category)

        val transaction = this.supportFragmentManager.beginTransaction()
        val itemDetailsFragment = ItemDetailsFragment()
        itemDetailsFragment.arguments = bundle

        transaction.replace(R.id.mainContainer, itemDetailsFragment)
        transaction.commit()
    }

    private fun setupSmoothBottomMenu() {
        val popupMenu = PopupMenu(this, null)
        popupMenu.inflate(R.menu.bottom_nav_menu)
        val menu = popupMenu.menu
        binding.bottomBar.setupWithNavController(menu, navController)
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

}
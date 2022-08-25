package com.example.ecomm

import android.graphics.Color
import android.os.Bundle
import android.widget.PopupMenu
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.example.ecomm.databinding.ActivityThirdHomeBinding
import com.example.ecomm.nav_fragments.ItemDetailsFragment
import com.example.ecomm.objects.Communicator


class ThirdHomeActivity : AppCompatActivity(), Communicator {
    private lateinit var navController: NavController
    private lateinit var binding : ActivityThirdHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityThirdHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        navController = findNavController(R.id.mainContainer)
        window.setNavigationBarColor(Color.parseColor("#432fbf"))
        setupSmoothBottomMenu()
//        val navHostFragment = supportFragmentManager.findFragmentById(R.id.mainContainer) as NavHostFragment
//        navController = navHostFragment.navController
//        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.nav_view)
//        NavigationUI.setupWithNavController(bottomNavigationView, navController)
    }

    override fun passDataCom(title: String, thumbnail: String, brand: String, price: String) {
        val bundle = Bundle()
        bundle.putString("title", title)
        bundle.putString("thumbnail", thumbnail)
        bundle.putString("brand", brand)
        bundle.putString("price", price)

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
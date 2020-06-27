package com.xiayiye.jetpackstudy.navigationdrawer

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.xiayiye.jetpackstudy.R
import kotlinx.android.synthetic.main.activity_navigation_drawer.*
import kotlinx.android.synthetic.main.drawer_content_layout.*

/**
 * @author XiaYiYe5
 * 时间：16点22分
 * NavigationDrawer学习
 */
class NavigationDrawerActivity : AppCompatActivity() {
    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_navigation_drawer)
        setSupportActionBar(toolbar)
        navController = findNavController(R.id.fragment_drawer)
//        appBarConfiguration = AppBarConfiguration(navController.graph, drawerLayout)
        val idOf = setOf<Int>(R.id.textFragment, R.id.pagerFragment, R.id.listFragment)
        appBarConfiguration = AppBarConfiguration(idOf, drawerLayout)
        setupActionBarWithNavController(navController, appBarConfiguration)
        navigationView.setupWithNavController(navController)
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}

package com.xiayiye.jetpackstudy.bottomnavigation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.Navigation
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import com.xiayiye.jetpackstudy.R
import kotlinx.android.synthetic.main.activity_bottom_navigation.*

class BottomNavigationActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bottom_navigation)
        val findNavController = Navigation.findNavController(this, R.id.fragmentTab)
//        val appBarConfiguration = AppBarConfiguration.Builder(findNavController.graph).build()
        //不出现箭头的方法一
        val appBarConfiguration = AppBarConfiguration.Builder(bnvTab.menu).build()
        //不出现箭头的方法二
//        val appBarConfiguration = AppBarConfiguration.Builder(R.id.firstFragment, R.id.twoFragment, R.id.threeFragment).build()
        NavigationUI.setupActionBarWithNavController(this, findNavController, appBarConfiguration)
        NavigationUI.setupWithNavController(bnvTab, findNavController)
    }
}

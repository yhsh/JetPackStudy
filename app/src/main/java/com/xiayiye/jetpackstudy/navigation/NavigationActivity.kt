package com.xiayiye.jetpackstudy.navigation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.xiayiye.jetpackstudy.R

class NavigationActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_navigation)
        val controller: NavController = Navigation.findNavController(this, R.id.fragment_index)
        NavigationUI.setupActionBarWithNavController(this, controller)
    }

    //添加返回按钮的方法
    override fun onSupportNavigateUp(): Boolean {
        return Navigation.findNavController(this, R.id.fragment_index).navigateUp()
    }
}

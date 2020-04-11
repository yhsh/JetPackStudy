package com.xiayiye.jetpackstudy.navigation.viewmodel

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.xiayiye.jetpackstudy.R

class NavigationViewModelActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_navigation_view_model)
        //设置是否有返回键的方法,结合下面的onSupportNavigateUp方法使用才有效
        NavigationUI.setupActionBarWithNavController(
            this,
            Navigation.findNavController(this, R.id.fragment_navigation)
        )
    }

    /**
     * 设置是否有返回键的方法
     */
    override fun onSupportNavigateUp(): Boolean {
        return Navigation.findNavController(this, R.id.fragment_navigation).navigateUp()
    }
}

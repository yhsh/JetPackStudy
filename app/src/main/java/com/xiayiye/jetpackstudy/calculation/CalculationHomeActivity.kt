package com.xiayiye.jetpackstudy.calculation

import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.setupActionBarWithNavController
import com.xiayiye.jetpackstudy.R

/**
 * @author xiayiye5
 * 2020年4月12 日 15点58分
 * 口算App首页
 */
class CalculationHomeActivity : AppCompatActivity() {
    lateinit var navController: NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calculation_home)
        //增加actionBar和返回按钮的方法
        navController = Navigation.findNavController(this, R.id.fragment_calculation)
        setupActionBarWithNavController(navController)
    }

    //增加actionBar和返回按钮的方法
    override fun onSupportNavigateUp(): Boolean {
        if (navController.currentDestination?.id == R.id.questionFragment) {
            AlertDialog.Builder(this).setTitle(getString(R.string.tip))
                .setMessage(getString(R.string.sure_exit))
                .setPositiveButton(getString(R.string.ok_back)) { dialog, p1 ->
                    dialog?.dismiss()
                    //关闭当前fragment
                    navController.navigateUp()
                }.setNegativeButton(getString(R.string.cancel_back), null).show()
        } else if (navController.currentDestination?.id == R.id.titleFragment) {
            //解决回到首页点击返回键无效的bug
            finish()
        } else {
            //其它页面直接返回首页
            navController.navigate(R.id.titleFragment)
        }
        return super.onSupportNavigateUp()
    }

    /**
     * 拦截实体返回键
     */
    override fun onBackPressed() {
        onSupportNavigateUp()
    }
}

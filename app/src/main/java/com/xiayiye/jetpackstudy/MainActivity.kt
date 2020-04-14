package com.xiayiye.jetpackstudy

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.xiayiye.jetpackstudy.basketball.BasketballActivity
import com.xiayiye.jetpackstudy.bottomnavigation.BottomNavigationActivity
import com.xiayiye.jetpackstudy.calculation.CalculationHomeActivity
import com.xiayiye.jetpackstudy.databinding.DataBindingActivity
import com.xiayiye.jetpackstudy.gallery.GalleryActivity
import com.xiayiye.jetpackstudy.lifecycle.LifecycleTimeActivity
import com.xiayiye.jetpackstudy.livedata.LiveDataActivity
import com.xiayiye.jetpackstudy.navigation.NavigationActivity
import com.xiayiye.jetpackstudy.navigation.viewmodel.NavigationViewModelActivity
import com.xiayiye.jetpackstudy.savestate.SaveStateActivity
import com.xiayiye.jetpackstudy.savestatejava.SaveStateJavaActivity
import com.xiayiye.jetpackstudy.viewmodel.ViewModelActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        lvStudy.setOnItemClickListener { adapterView, view, position, args -> jumpPage(position) }
    }

    private fun jumpPage(position: Int) {
        when (position) {
            0 -> goToPage(ViewModelActivity::class.java)
            1 -> goToPage(LiveDataActivity::class.java)
            2 -> goToPage(DataBindingActivity::class.java)
            3 -> goToPage(BasketballActivity::class.java)
            4 -> goToPage(SaveStateActivity::class.java)
            5 -> goToPage(SaveStateJavaActivity::class.java)
            6 -> goToPage(NavigationActivity::class.java)
            7 -> goToPage(NavigationViewModelActivity::class.java)
            8 -> goToPage(CalculationHomeActivity::class.java)
            9 -> goToPage(LifecycleTimeActivity::class.java)
            10 -> goToPage(BottomNavigationActivity::class.java)
            11 -> goToPage(GalleryActivity::class.java)
        }
    }

    // * 表示所有
    private fun goToPage(clazz: Class<*>) {
        startActivity(Intent(this, clazz))
    }
}

package com.xiayiye.jetpackstudy.viewpager2

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.android.material.tabs.TabLayoutMediator
import com.xiayiye.jetpackstudy.R
import kotlinx.android.synthetic.main.activity_view_pager2.*

/**
 * ViewPager2 的基本用法
 */
class ViewPager2Activity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_pager2)
        viewPager2.adapter = object : FragmentStateAdapter(this) {
            override fun getItemCount(): Int {
                return 3
            }

            override fun createFragment(position: Int) = when (position) {
                0 -> RotateViewPager2Fragment()
                1 -> ScaleViewPager2Fragment()
                else -> TranslateViewPager2Fragment()
            }
        }
        //TabLayoutMediator这个类只有新版本的 material-1.1 以上的包中才有
        TabLayoutMediator(tableLayout, viewPager2) { tab, position ->
            when (position) {
                0 -> tab.text = "旋转"
                1 -> tab.text = "缩放"
                else -> tab.text = "平移"
            }
        }.attach()
    }
}

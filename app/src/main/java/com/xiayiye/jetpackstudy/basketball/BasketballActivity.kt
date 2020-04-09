package com.xiayiye.jetpackstudy.basketball

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.xiayiye.jetpackstudy.R
import com.xiayiye.jetpackstudy.databinding.ActivityBasketballBinding

/**
 * 2020/4/9 17点41分
 * 篮球计分器页面
 */
class BasketballActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        title = "篮球计分器"
        val basketballViewModel: BasketballViewModel =
            ViewModelProvider(this).get(BasketballViewModel::class.java)
        val binding = DataBindingUtil.setContentView<ActivityBasketballBinding>(
            this,
            R.layout.activity_basketball
        )
        binding.scoreData = basketballViewModel
        binding.lifecycleOwner = this
    }
}

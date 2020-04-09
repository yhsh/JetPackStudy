package com.xiayiye.jetpackstudy.viewmodel

//import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.xiayiye.jetpackstudy.R
import kotlinx.android.synthetic.main.activity_view_model.*

//通过使用ViewModel保持数据在横竖屏切换，更改字体的时候不丢失
class ViewModelActivity : AppCompatActivity() {
    lateinit var showViewModel: ShowDataViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        title = "ViewModel保存数据不丢失"
        setContentView(R.layout.activity_view_model)
        showViewModel = ViewModelProvider(this).get(ShowDataViewModel::class.java)
        //设置恢复的数据
        tvResult.text = showViewModel.number.toString()
        btAddOne.setOnClickListener {
            showViewModel.number += 1;tvResult.text = showViewModel.number.toString()
        }
        btAddTwo.setOnClickListener {
            showViewModel.number += 2;tvResult.text = showViewModel.number.toString()
        }
    }
}

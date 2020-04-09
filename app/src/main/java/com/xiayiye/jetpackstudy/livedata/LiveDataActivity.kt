package com.xiayiye.jetpackstudy.livedata

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.xiayiye.jetpackstudy.R
import kotlinx.android.synthetic.main.activity_live_data.*

class LiveDataActivity : AppCompatActivity() {
    lateinit var viewModelWithLiveData: ViewModelWithLiveData
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        title = "LiveData学习"
        setContentView(R.layout.activity_live_data)
        viewModelWithLiveData = ViewModelProvider(this).get(ViewModelWithLiveData::class.java)
        viewModelWithLiveData.getLikeNumber().observe(this, object : Observer<Int> {
            override fun onChanged(value: Int?) {
                tvLikeResult.text = value.toString()
            }
        })
        ibLike.setOnClickListener { viewModelWithLiveData.addLikeNumber(1) }
        ibDisLike.setOnClickListener { viewModelWithLiveData.addLikeNumber(-1) }
    }
}

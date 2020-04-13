package com.xiayiye.jetpackstudy.lifecycle

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.SystemClock
import com.xiayiye.jetpackstudy.R
import kotlinx.android.synthetic.main.activity_lifecycles.*

/**
 * @author 下一页5
 * @since 2020年4月13日 10点46分
 * @suppress 计时器
 */
class LifecycleTimeActivity : AppCompatActivity() {
    companion object {
        var stopTime: Long = 0
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lifecycles)
        //下面使用的是系统开机后开始计时,下面这行可以不设置,默认的
        cmTime.base = SystemClock.elapsedRealtime()

        //通过使用Lifecycle设置第二个计时器,一行代码搞定
        lifecycle.addObserver(myCmTime)
    }

    override fun onPause() {
        super.onPause()
        //记录计时的时间
        stopTime = SystemClock.elapsedRealtime() - cmTime.base
    }

    override fun onResume() {
        super.onResume()
        //重新恢复记录的时间
        cmTime.base = SystemClock.elapsedRealtime() - stopTime
        //开始计时
        cmTime.start()
    }

}

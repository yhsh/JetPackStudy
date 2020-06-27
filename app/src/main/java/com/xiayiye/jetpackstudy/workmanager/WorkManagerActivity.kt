package com.xiayiye.jetpackstudy.workmanager

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.work.*
import com.xiayiye.jetpackstudy.R
import kotlinx.android.synthetic.main.activity_work_manager.*

/**
 * @author XiaYiYe5
 * 时间：12点15分
 * 说明：WorkManager 学习页面
 */
class WorkManagerActivity : AppCompatActivity(),
    SharedPreferences.OnSharedPreferenceChangeListener {
    override fun onSharedPreferenceChanged(p0: SharedPreferences?, p1: String?) {
        //当SP数据发生改变的时候自动更新数据
        updateView()
    }

    private val inputDataKey: String = "input"
    private val inputDataA: String = "我来了A"
    private val inputDataB: String = "我来了B"
    //创建 WorkManager实例
    private var workManager = WorkManager.getInstance(this)
    //当网络连接的时候载开始执行任务
    private val constraints =
        Constraints.Builder().setRequiredNetworkType(NetworkType.CONNECTED).build()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_work_manager)
        //注册SP的监听
        getSharedPreferences(
            "workManager",
            Context.MODE_PRIVATE
        ).registerOnSharedPreferenceChangeListener(this)
        //初始化数据
        updateView()
        val workRequestA = createTask(inputDataA)
        val workRequestB = createTask(inputDataB)
        //设置执行任务的观察者监听
        workManager.getWorkInfoByIdLiveData(workRequestA.id).observe(this, Observer {
            Log.e("打印执行任务的状态", it.state.toString())
            if (it.state == WorkInfo.State.SUCCEEDED) {
                //打印任务执行成功后回传的数据
                Log.e("打印任务执行成功后回传的数据", it.outputData.getString("output") + "")
            }
        })
        btStartWork.setOnClickListener {
            //添加工作队列
//            workManager.enqueue(workRequest)
            workManager.beginWith(workRequestA).then(workRequestB).enqueue()
        }
    }

    private fun createTask(name: String): OneTimeWorkRequest {
        return OneTimeWorkRequestBuilder<MyWorker>()
            //传递数据的方式
            //            .setInputData(workDataOf(inputDataKey to inputData))
            .setInputData(workDataOf(Pair(inputDataKey, name)))
            //执行任务的类型
            .setConstraints(constraints).build()
    }

    private fun updateView() {
        tvShowOneTask.text =
            getSharedPreferences("workManager", Context.MODE_PRIVATE).getInt(inputDataA, -1)
                .toString()
        tvShowTwoTask.text =
            getSharedPreferences("workManager", Context.MODE_PRIVATE).getInt(inputDataB, -1)
                .toString()
    }
}

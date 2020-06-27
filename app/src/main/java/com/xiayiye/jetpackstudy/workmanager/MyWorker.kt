package com.xiayiye.jetpackstudy.workmanager

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
import androidx.work.workDataOf

/*
 * Copyright (c) 2020, smuyyh@gmail.com All Rights Reserved.
 * #                                                   #
 * #                       _oo0oo_                     #
 * #                      o8888888o                    #
 * #                      88" . "88                    #
 * #                      (| -_- |)                    #
 * #                      0\  =  /0                    #
 * #                    ___/`---'\___                  #
 * #                  .' \\|     |# '.                 #
 * #                 / \\|||  :  |||# \                #
 * #                / _||||| -:- |||||- \              #
 * #               |   | \\\  -  #/ |   |              #
 * #               | \_|  ''\---/''  |_/ |             #
 * #               \  .-\__  '-'  ___/-. /             #
 * #             ___'. .'  /--.--\  `. .'___           #
 * #          ."" '<  `.___\_<|>_/___.' >' "".         #
 * #         | | :  `- \`.;`\ _ /`;.`/ - ` : | |       #
 * #         \  \ `_.   \_ __\ /__ _/   .-` /  /       #
 * #     =====`-.____`.___ \_____/___.-`___.-'=====    #
 * #                       `=---='                     #
 * #     ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~   #
 * #                                                   #
 * #               佛祖保佑         永无BUG            #
 * #                                                   #
 */

/**
 * @author 下一页5（轻飞扬）
 * 创建时间：2020/6/27 12:16
 * 个人小站：http://yhsh.wap.ai(已挂)
 * 最新小站：http://www.iyhsh.icoc.in
 * 联系作者：企鹅 13343401268
 * 博客地址：http://blog.csdn.net/xiayiye5
 * 项目名称：JetPackStudy
 * 文件包名：com.xiayiye.jetpackstudy.workmanager
 * 文件说明：
 */
class MyWorker(val context: Context, workerParams: WorkerParameters) :
    Worker(context, workerParams) {
    override fun doWork(): Result {
        //获取传递的数据
        val input = inputData.getString("input")
//        Log.e("打印传递过来的数据", input + "")
        Log.e("打印线程", Thread.currentThread().name)
//        Looper.prepare()
//        Toast.makeText(context, "开启工作了", Toast.LENGTH_SHORT).show()
//        Looper.loop()
//        Log.e("打印任务", "开始工作了")
        //延迟3秒发送成功的请求
        Thread.sleep(3000)
//        SystemClock.sleep(3000)
//        Looper.prepare()
//        Toast.makeText(context, "工作完成", Toast.LENGTH_SHORT).show()
//        Looper.prepare()
//        Log.e("打印任务", "工作完成了")
        val sp = applicationContext.getSharedPreferences("workManager", Context.MODE_PRIVATE)
        var number = sp.getInt(input, 0)
        sp.edit().putInt(input, ++number).apply()
        return Result.success(workDataOf("output" to "成功后回调的数据"))
    }
}
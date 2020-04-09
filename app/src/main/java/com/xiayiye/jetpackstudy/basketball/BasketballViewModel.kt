package com.xiayiye.jetpackstudy.basketball

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

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
 * 创建时间：2020/4/9 17:42
 * 个人小站：http://yhsh.wap.ai(已挂)
 * 最新小站：http://www.iyhsh.icoc.in
 * 联系作者：企鹅 13343401268
 * 博客地址：http://blog.csdn.net/xiayiye5
 * 项目名称：JetPackStudy
 * 文件包名：com.xiayiye.jetpackstudy.basketball
 * 文件说明：
 */
class BasketballViewModel : ViewModel() {
    var backA: Int? = 0
    var backB: Int? = 0
    var basketballScoreA: MutableLiveData<Int> = MutableLiveData<Int>(0)
    //    var basketballScoreB: MutableLiveData<Int> = MutableLiveData<Int>(0)
    val basketballScoreB by lazy { MutableLiveData<Int>(0) }

    /**
     * A 队加分
     */
    fun teamAddA(scoreA: Int) {
        lastScore()
        basketballScoreA.value = basketballScoreA.value?.plus(scoreA)
    }

    /**
     * B 队加分
     */
    fun teamAddB(scoreB: Int) {
        lastScore()
        basketballScoreB.value = basketballScoreB.value?.plus(scoreB)
    }

    /**
     * 重置分数
     */
    fun resetScore() {
        lastScore()
        basketballScoreA.value = 0
        basketballScoreB.value = 0
    }

    /**
     * 记录上次的分数
     */
    private fun lastScore() {
        backA = basketballScoreA.value
        backB = basketballScoreB.value
        Log.e("打印最后一次的值：", "${backA}------${backB}")
    }

    /**
     * 撤销的方法
     */
    fun unDo() {
        basketballScoreA.value = backA
        basketballScoreB.value = backB
    }
}
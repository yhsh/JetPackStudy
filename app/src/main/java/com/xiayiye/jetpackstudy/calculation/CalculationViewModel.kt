package com.xiayiye.jetpackstudy.calculation

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import java.util.*

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
 * 创建时间：2020/4/12 17:50
 * 个人小站：http://yhsh.wap.ai(已挂)
 * 最新小站：http://www.iyhsh.icoc.in
 * 联系作者：企鹅 13343401268
 * 博客地址：http://blog.csdn.net/xiayiye5
 * 项目名称：JetPackStudy
 * 文件包名：com.xiayiye.jetpackstudy.calculation
 * 文件说明：
 */
class CalculationViewModel(application: Application, private val handler: SavedStateHandle) :
    AndroidViewModel(application) {
    var winFlag = false

    companion object {
        const val KEY_HIGH_SCORE = "Key_high_score"
        const val KEY_LEFT_NUMBER = "Key_left_score"
        const val KEY_RIGHT_NUMBER = "Key_right_score"
        const val KEY_OPERATOR = "Key_operator"
        const val KEY_ANSWER = "Key_answer"
        const val CURRENT_SCORE = "current_score"
        const val SAVE_SHP_DATA_NAME = "save_shp_data_name"
        const val LEVEL = 20
    }

    init {
        if (!handler.contains(KEY_HIGH_SCORE)) {
            val shp = application.getSharedPreferences(SAVE_SHP_DATA_NAME, Context.MODE_PRIVATE)
            handler.set(KEY_HIGH_SCORE, shp.getInt(KEY_HIGH_SCORE, 0))
            handler.set(KEY_LEFT_NUMBER, 0)
            handler.set(KEY_RIGHT_NUMBER, 0)
            handler.set(KEY_OPERATOR, "+")
            handler.set(KEY_ANSWER, 0)
            handler.set(CURRENT_SCORE, 0)
        }

    }

    val getLeftNumber: MutableLiveData<Int>
        get() {
            return handler.getLiveData(KEY_LEFT_NUMBER)
        }
    val getRightNumber: MutableLiveData<Int>
        get() {
            return handler.getLiveData(KEY_RIGHT_NUMBER)
        }
    val getOperatorNumber: MutableLiveData<String>
        get() {
            return handler.getLiveData(KEY_OPERATOR)
        }
    val getHighScore: MutableLiveData<Int>
        get() {
            return handler.getLiveData(KEY_HIGH_SCORE)
        }
    val getCurrentScore: MutableLiveData<Int>
        get() {
            return handler.getLiveData(CURRENT_SCORE)
        }
    val getAnswer: MutableLiveData<Int>
        get() {
            return handler.getLiveData(KEY_ANSWER)
        }

    fun generator() {
        val random = Random()
        val x = random.nextInt(LEVEL) + 1
        val y = random.nextInt(LEVEL) + 1
        if (x % 2 == 0) {
            getOperatorNumber.value = "+"
            if (x > y) {
                getAnswer.value = x
                getLeftNumber.value = y
                getRightNumber.value = x - y
            } else {
                getAnswer.value = y
                getLeftNumber.value = x
                getRightNumber.value = y - x
            }
        } else {
            getOperatorNumber.value = "-"
            if (x > y) {
                getAnswer.value = y
                getLeftNumber.value = x
                getRightNumber.value = x - y
            } else {
                getAnswer.value = x
                getLeftNumber.value = y
                getRightNumber.value = y - x
            }
        }
    }

    /**
     * 保存最高分的操作
     */
    fun save() {
        getApplication<Application>().getSharedPreferences(SAVE_SHP_DATA_NAME, Context.MODE_PRIVATE)
            .edit().putInt(KEY_HIGH_SCORE, getHighScore.value!!).apply()
    }

    fun answerCorrect() {
        getCurrentScore.value = getCurrentScore.value?.plus(1)
        if (getCurrentScore.value!! > getHighScore.value!!) {
            //将当前的最高记录分数设置给最高记录
            getHighScore.value = getCurrentScore.value
            winFlag = true
        }
        //生成一道新题目
        generator()
    }
}
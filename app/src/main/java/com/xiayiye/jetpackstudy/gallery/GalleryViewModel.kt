package com.xiayiye.jetpackstudy.gallery

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.google.gson.Gson
import kotlin.math.ceil

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
 * 创建时间：2020/4/14 15:25
 * 个人小站：http://yhsh.wap.ai(已挂)
 * 最新小站：http://www.iyhsh.icoc.in
 * 联系作者：企鹅 13343401268
 * 博客地址：http://blog.csdn.net/xiayiye5
 * 项目名称：JetPackStudy
 * 文件包名：com.xiayiye.jetpackstudy.gallery
 * 文件说明：
 */
class GalleryViewModel(application: Application) : AndroidViewModel(application) {
    companion object {
        const val DATA_STATUS_CAN_LOAD_MORE = 0
        const val DATA_STATUS_NO_MORE = 1
        //网络错误的标识
        const val DATA_STATUS_NETWORK_ERROR = 2
    }

    private val _photoListView = MutableLiveData<List<PhotoItem>>()
    val photoListView: LiveData<List<PhotoItem>> get() = _photoListView
    //是否需要滑动到顶部
    var needToScrollerToTop = true
    private val keywords = arrayOf(
        "lotus", "car", "husky", "cat", "dog", "pet", "cloud", "fruit", "grape", "banana", "bamboo",
        "flower", "sky", "sea", "desert", "mountain", "Great Wall", "Alaskan", "Himalaya", "earth",
        "moon", "Racing Girl", "auto show", "breast model", "pleura", "leg model", "model"
    )
    private var prePage: Int = 50
    private var currentPage: Int = 1
    private var totalPage: Int = 1
    private var currentKey = "dog"
    private var isNewQuery = true
    private var isLoading = false

    init {
        //全局初始化数据
        resetQuery()
    }

    fun resetQuery() {
        currentPage = 1
        totalPage = 1
        currentKey = keywords.random()
        isNewQuery = true
        needToScrollerToTop = true
        fetchData()
    }

    fun fetchData() {
        Log.e("打印网址", getUrl())
        //正在加载就不做任何操作
        if (isLoading) return
        isLoading = true
        //所以数据加载完毕
        if (currentPage > totalPage) return
        val stringRequest = StringRequest(Request.Method.GET, getUrl(), Response.Listener {
            with(Gson().fromJson(it, GalleryBean::class.java)) {
                //设置搜索结果的总页数
                totalPage = ceil(totalHits.toDouble() / prePage).toInt()
                if (isNewQuery) {
                    _photoListView.value = hits
                } else {
                    //如果不是刷新就将新请求的数据添加到集合里面
                    _photoListView.value = arrayListOf(_photoListView.value!!, hits).flatten()
                }
            }
            isLoading = false
            isNewQuery = false
            currentPage++
        }, Response.ErrorListener {
            println(it.printStackTrace())
            isLoading = false
        })
        //添加到请求队列中
        VolleySingleton.getInstance(getApplication()).requestQueue.add(stringRequest)
    }

    private fun getUrl(): String {
        return "https://pixabay.com/api/?key=16032344-bb89de8b100a3e01939c38139&q=$currentKey&per_page=$prePage&page=$currentPage"
    }

}
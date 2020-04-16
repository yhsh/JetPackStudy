package com.xiayiye.jetpackstudy.paging

import android.content.Context
import androidx.paging.PageKeyedDataSource
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.google.gson.Gson
import com.xiayiye.jetpackstudy.gallery.GalleryBean
import com.xiayiye.jetpackstudy.gallery.PhotoItem
import com.xiayiye.jetpackstudy.gallery.VolleySingleton

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
 * 创建时间：2020/4/16 15:25
 * 个人小站：http://yhsh.wap.ai(已挂)
 * 最新小站：http://www.iyhsh.icoc.in
 * 联系作者：企鹅 13343401268
 * 博客地址：http://blog.csdn.net/xiayiye5
 * 项目名称：JetPackStudy
 * 文件包名：com.xiayiye.jetpackstudy.paging
 * 文件说明：paging分页库
 */
class PixBayDataSource(private val context: Context) : PageKeyedDataSource<Int, PhotoItem>() {
    //搜索的关键字
    private val queryKey = arrayOf(
        "lotus", "car", "husky", "cat", "dog", "pet", "cloud", "fruit", "grape", "banana", "bamboo",
        "flower", "sky", "sea", "desert", "mountain", "Great Wall", "Alaskan", "Himalaya", "earth",
        "moon", "Racing Girl", "auto show", "breast model", "pleura", "leg model", "model", "girl"
    ).random()

    override fun loadInitial(
        params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, PhotoItem>
    ) {
        StringRequest(Request.Method.GET,
            "https://pixabay.com/api/?key=16032344-bb89de8b100a3e01939c38139&q=$queryKey&per_page=50&page=1",
            Response.Listener {
                val dataList = Gson().fromJson<GalleryBean>(it, GalleryBean::class.java).hits
                //添加数据
                callback.onResult(dataList, null, 2)
            },
            Response.ErrorListener {
                println("请求错误${it.printStackTrace()}")
            }
        ).also { VolleySingleton.getInstance(context).requestQueue.add(it) }
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, PhotoItem>) {
        StringRequest(
            Request.Method.GET,
            "https://pixabay.com/api/?key=16032344-bb89de8b100a3e01939c38139&q=$queryKey&per_page=$50&page=${params.key}",
            Response.Listener {
                val dataList = Gson().fromJson<GalleryBean>(it, GalleryBean::class.java).hits
                callback.onResult(dataList, params.key + 1)
            },
            Response.ErrorListener { println("加载更多错误${it.printStackTrace()}") }
        ).also { VolleySingleton.getInstance(context).requestQueue.add(it) }
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, PhotoItem>) {

    }
}
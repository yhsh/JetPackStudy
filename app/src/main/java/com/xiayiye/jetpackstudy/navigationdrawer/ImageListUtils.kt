package com.xiayiye.jetpackstudy.navigationdrawer

import com.xiayiye.jetpackstudy.R

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
 * 创建时间：2020/6/27 18:44
 * 个人小站：http://yhsh.wap.ai(已挂)
 * 最新小站：http://www.iyhsh.icoc.in
 * 联系作者：企鹅 13343401268
 * 博客地址：http://blog.csdn.net/xiayiye5
 * 项目名称：JetPackStudy
 * 文件包名：com.xiayiye.jetpackstudy.navigationdrawer
 * 文件说明：获取图片集合的工具类
 */
object ImageListUtils {
    fun getImgData(): List<Int> {
        val imgList = intArrayOf(
            R.drawable.ic_fail_icon,
            R.drawable.ic_success_icon,
            R.drawable.ic_photo_actual,
            R.drawable.ic_favorite_red,
            R.drawable.ic_file_download_gray,
            R.drawable.ic_file_download_white,
            R.drawable.ic_loop_black_24dp,
            R.drawable.ic_undo_black_24dp,
            R.drawable.ic_looks_three_black_50dp,
            R.drawable.ic_thumb_up_black_50dp,
            R.drawable.ic_looks_one_black_50dp,
            R.drawable.ic_looks_two_black_50dp
        )
        return Array(100) {
            imgList.random()
        }.toList()
    }
}
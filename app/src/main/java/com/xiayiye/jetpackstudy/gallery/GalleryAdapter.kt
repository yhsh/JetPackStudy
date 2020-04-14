package com.xiayiye.jetpackstudy.gallery

import android.graphics.Color
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.xiayiye.jetpackstudy.R
import kotlinx.android.synthetic.main.gallery_cell.view.*

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
 * 创建时间：2020/4/14 16:05
 * 个人小站：http://yhsh.wap.ai(已挂)
 * 最新小站：http://www.iyhsh.icoc.in
 * 联系作者：企鹅 13343401268
 * 博客地址：http://blog.csdn.net/xiayiye5
 * 项目名称：JetPackStudy
 * 文件包名：com.xiayiye.jetpackstudy.gallery
 * 文件说明：
 */
class GalleryAdapter : ListAdapter<PhotoItem, GalleryAdapter.MyViewHolder>(DifferentCallBack) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val myViewHolder = MyViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.gallery_cell, parent, false
            )
        )
        //点击跳转大图浏览
        myViewHolder.itemView.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.action_galleryFragment_to_photoFragment))
        return myViewHolder
    }

    object DifferentCallBack : DiffUtil.ItemCallback<PhotoItem>() {
        override fun areItemsTheSame(oldItem: PhotoItem, newItem: PhotoItem): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: PhotoItem, newItem: PhotoItem): Boolean {
            return oldItem.id == newItem.id
        }
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.itemView.shimmerLayoutCell.apply {
            setShimmerColor(0x55ffffff)
            //闪动的角度
            setShimmerAngle(0)
            //开始闪动
            startShimmerAnimation()
        }
        //加载图片
        Glide.with(holder.itemView.context).load(getItem(position).webformatURL)
            .placeholder(R.drawable.ic_photo_actual)
            .listener(object : RequestListener<Drawable> {
                override fun onLoadFailed(
                    e: GlideException?, model: Any?,
                    target: Target<Drawable>?, isFirstResource: Boolean
                ): Boolean {
                    return false
                }

                override fun onResourceReady(
                    resource: Drawable?,
                    model: Any?,
                    target: Target<Drawable>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean
                ): Boolean {
                    return false.also { holder.itemView.shimmerLayoutCell?.stopShimmerAnimation() }
                }

            })
            .into(holder.itemView.ivShowGallery)
        /* holder.itemView.shimmerLayoutCell.run {

         }
         holder.itemView.shimmerLayoutCell.let {

         }
         holder.itemView.shimmerLayoutCell.also {

         }*/
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }
}
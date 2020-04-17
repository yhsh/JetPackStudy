package com.xiayiye.jetpackstudy.paging

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.xiayiye.jetpackstudy.R
import com.xiayiye.jetpackstudy.gallery.PhotoItem
import kotlinx.android.synthetic.main.gallery_cell.view.*
import java.util.*
import kotlin.collections.ArrayList

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
class GalleryAdapterPaging :
    PagedListAdapter<PhotoItem, GalleryAdapterPaging.MyViewHolder>(DifferentCallBackPaging) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val myViewHolder = MyViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.gallery_cell, parent, false
            )
        )
        //带参数传递
        myViewHolder.itemView.setOnClickListener {
            val jumpPage: Int
            Bundle().apply {
                //防止角标为-1导致奔溃的情况
                if (myViewHolder.adapterPosition >= 0) {
                    if (Random().nextBoolean()) {
                        jumpPage = R.id.action_galleryFragment_to_photoFragment
                        putParcelable("photo", getItem(myViewHolder.adapterPosition))
                    } else {
                        //将整个数据列表传递过去
                        putParcelableArrayList("photo_list", ArrayList(currentList!!))
                        //当前点击的位置传递过去
                        putInt("photo_position", myViewHolder.adapterPosition)
                        jumpPage = R.id.action_galleryFragment_to_viewPager2ImageFragment
                    }
                    myViewHolder.itemView.findNavController()
                        .navigate(jumpPage, this)
                }
            }
        }
        return myViewHolder
    }

    object DifferentCallBackPaging : DiffUtil.ItemCallback<PhotoItem>() {
        override fun areItemsTheSame(oldItem: PhotoItem, newItem: PhotoItem): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: PhotoItem, newItem: PhotoItem): Boolean {
            return oldItem == newItem
        }
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        if (position == itemCount - 1) {
            //脚布局不加载下面的数据直接返回
            return
        }
        //设置图片高度
        val item = getItem(position) ?: return
        holder.itemView.ivShowGallery.layoutParams.height = item.webformatHeight
        holder.itemView.tvDescribe.text = item.user
        holder.itemView.tvLikes.text = item.likes.toString()
        holder.itemView.tvFavorite.text = item.favorites.toString()
        holder.itemView.shimmerLayoutCell.apply {
            setShimmerColor(0x55ffffff)
            //闪动的角度
            setShimmerAngle(0)
            //开始闪动
            startShimmerAnimation()
        }
        //加载图片
        Glide.with(holder.itemView.context).load(item.webformatURL)
            .placeholder(R.drawable.place_holder)
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
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}
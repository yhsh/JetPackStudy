package com.xiayiye.jetpackstudy.gallery

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.xiayiye.jetpackstudy.R
import com.xiayiye.jetpackstudy.gallery.Constant.Companion.DATA_STATUS_CAN_LOAD_MORE
import com.xiayiye.jetpackstudy.gallery.Constant.Companion.DATA_STATUS_NETWORK_ERROR
import com.xiayiye.jetpackstudy.gallery.Constant.Companion.DATA_STATUS_NO_MORE
import com.xiayiye.jetpackstudy.gallery.Constant.Companion.FOOT_VIEW_STATUS
import kotlinx.android.synthetic.main.gallery_cell.view.*
import kotlinx.android.synthetic.main.gallery_footer.view.*
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
class GalleryAdapter(private val galleryViewModel: GalleryViewModel) :
    ListAdapter<PhotoItem, GalleryAdapter.MyViewHolder>(DifferentCallBack) {
    companion object {
        const val NORMAL_VIEW_TYPE = 0
        const val FOOTER_VIEW_TYPE = 1
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        if (viewType == FOOTER_VIEW_TYPE) {
            //脚布局
            LayoutInflater.from(parent.context).inflate(R.layout.gallery_footer, parent, false)
                .also {
                    //设置进度条占满整个屏幕宽度的方法
                    if (it.layoutParams is StaggeredGridLayoutManager.LayoutParams) {
                        println("打印布局1:${it.layoutParams}")
                        (it.layoutParams as StaggeredGridLayoutManager.LayoutParams).isFullSpan =
                            true
                    } else {
                        println("打印布局2:${it.layoutParams}")
                        (it.layoutParams as RecyclerView.LayoutParams).width =
                            LinearLayout.LayoutParams.MATCH_PARENT
                    }
                    it.setOnClickListener { itemView ->
                        //加载更多整个布局的点击事件
                        itemView.progressBarLoadMore.visibility = View.VISIBLE
                        itemView.tvLoading.text = "正在加载中……"
                        galleryViewModel.fetchData()
                    }
                    return MyViewHolder(it)
                }
        }
        val myViewHolder = MyViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.gallery_cell, parent, false
            )
        )
        //点击跳转大图浏览,不带参数
//        myViewHolder.itemView.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.action_galleryFragment_to_photoFragment))
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
                        putParcelableArrayList("photo_list", ArrayList(currentList))
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

    object DifferentCallBack : DiffUtil.ItemCallback<PhotoItem>() {
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
            with(holder.itemView) {
                when (FOOT_VIEW_STATUS) {
                    DATA_STATUS_CAN_LOAD_MORE -> {
                        //正常加载中,显示加载更多的整个布局
                        progressBarLoadMore.visibility = View.VISIBLE
                        tvLoading.text = holder.itemView.context.getString(R.string.loading)
                        //加载更多整个控件不可点击
                        isClickable = false
                    }
                    DATA_STATUS_NO_MORE -> {
                        //等于1 说明已加载完毕所有数据
                        progressBarLoadMore.visibility = View.GONE
                        tvLoading.text = "全部加载完毕！"
                        //加载更多整个控件不可点击
                        isClickable = false
                    }
                    DATA_STATUS_NETWORK_ERROR -> {
                        //等于1 说明已加载完毕所有数据
                        progressBarLoadMore.visibility = View.GONE
                        tvLoading.text = "网络错误,点击重试！"
                        //加载更多整个控件可以点击
                        isClickable = true
                    }
                }
            }
            return
        }
        //设置图片高度
        holder.itemView.ivShowGallery.layoutParams.height = getItem(position).webformatHeight
        holder.itemView.tvDescribe.text = getItem(position).user
        holder.itemView.tvLikes.text = getItem(position).likes.toString()
        holder.itemView.tvFavorite.text = getItem(position).favorites.toString()
        holder.itemView.shimmerLayoutCell.apply {
            setShimmerColor(0x55ffffff)
            //闪动的角度
            setShimmerAngle(0)
            //开始闪动
            startShimmerAnimation()
        }
        //加载图片
        Glide.with(holder.itemView.context).load(getItem(position).webformatURL)
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
        /* holder.itemView.shimmerLayoutCell.run {

         }
         holder.itemView.shimmerLayoutCell.let {

         }
         holder.itemView.shimmerLayoutCell.also {

         }
        with(holder.itemView.shimmerLayoutCell){
            stopShimmerAnimation()
        }*/
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }

    /**
     * 增加脚布局一个item
     */
    override fun getItemCount(): Int = super.getItemCount() + 1

    override fun getItemViewType(position: Int): Int =
        if (position == itemCount - 1) FOOTER_VIEW_TYPE else NORMAL_VIEW_TYPE
}
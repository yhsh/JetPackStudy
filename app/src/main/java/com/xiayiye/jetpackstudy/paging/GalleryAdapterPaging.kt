package com.xiayiye.jetpackstudy.paging

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.navigation.findNavController
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.xiayiye.jetpackstudy.R
import com.xiayiye.jetpackstudy.gallery.PhotoItem
import kotlinx.android.synthetic.main.gallery_cell.view.*
import kotlinx.android.synthetic.main.gallery_footer_paging.view.*
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
class GalleryAdapterPaging(private val galleryViewModelPaging: GalleryViewModelPaging) :
    PagedListAdapter<PhotoItem, RecyclerView.ViewHolder>(DifferentCallBackPaging) {
    private var netWorkStatus: NetWorkStatus? = null
    private var hasFooter = false

    init {
        //进入图片浏览页面的时候自动重新加载数据
        galleryViewModelPaging.retry()
    }

    /**
     * 更新网络状态的方法
     */
    fun updateNetworkStatus(netWorkStatus: NetWorkStatus?) {
        this.netWorkStatus = netWorkStatus
        if (netWorkStatus == NetWorkStatus.INITIAL_LOADING) hideFooter() else showFooter()
    }

    /**
     * 隐藏加载更多整个布局
     */
    fun hideFooter() {
        if (hasFooter) {
            //移除加载更多整个布局
            notifyItemRemoved(itemCount - 1)
            hasFooter = false
        }
    }

    /**
     * 显示加载更多整个布局
     */
    fun showFooter() {
        if (hasFooter) {
            //有这个布局的话直接刷新这个布局即可
            notifyItemChanged(itemCount - 1)
        } else {
            //没有此布局的话就添加这个布局,必须先修改hasFooter的值才会添加加载更多整个布局
            hasFooter = true
            notifyItemInserted(itemCount - 1)
        }
    }

    override fun getItemCount(): Int {
        return super.getItemCount() + if (hasFooter) 1 else 0
    }

    override fun getItemViewType(position: Int): Int {
        return if (hasFooter && position == itemCount - 1) R.layout.gallery_footer_paging else R.layout.gallery_cell
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            R.layout.gallery_cell -> PhotoViewHolder.newInstance(parent).also { photoHolder ->
                //带参数传递
                photoHolder.itemView.setOnClickListener {
                    val jumpPage: Int
                    Bundle().apply {
                        //防止角标为-1导致奔溃的情况
                        if (photoHolder.adapterPosition >= 0) {
                            if (Random().nextBoolean()) {
                                jumpPage = R.id.action_galleryFragment_to_photoFragment
                                putParcelable("photo", getItem(photoHolder.adapterPosition))
                            } else {
                                //将整个数据列表传递过去
                                putParcelableArrayList("photo_list", ArrayList(currentList!!))
                                //当前点击的位置传递过去
                                putInt("photo_position", photoHolder.adapterPosition)
                                jumpPage = R.id.action_galleryFragment_to_viewPager2ImageFragment
                            }
                            photoHolder.itemView.findNavController()
                                .navigate(jumpPage, this)
                        }
                    }
                }
            }
            else -> FooterViewHolder.newInstance(parent).also {
                //点击重试的方法
                it.itemView.setOnClickListener { galleryViewModelPaging.retry() }
            }
        }
    }

    object DifferentCallBackPaging : DiffUtil.ItemCallback<PhotoItem>() {
        override fun areItemsTheSame(oldItem: PhotoItem, newItem: PhotoItem): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: PhotoItem, newItem: PhotoItem): Boolean {
            return oldItem == newItem
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (position == itemCount - 1) {
            //脚布局,绑定脚布局的方法
            (holder as FooterViewHolder).bindWithNetWorkStatus(netWorkStatus)
            return
        }
        //绑定正常布局数据
        val item = getItem(position) ?: return
        (holder as PhotoViewHolder).bindWithPhotoItem(item)
    }

    /**
     * 正常页面的布局
     */
    class PhotoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        companion object {
            fun newInstance(parent: ViewGroup): PhotoViewHolder {
                val inflate = LayoutInflater.from(parent.context)
                    .inflate(R.layout.gallery_cell, parent, false)
                return PhotoViewHolder(inflate)
            }
        }

        fun bindWithPhotoItem(photoItem: PhotoItem) {
            itemView.shimmerLayoutCell.apply {
                setShimmerColor(0x55ffffff)
                //闪动的角度
                setShimmerAngle(0)
                //开始闪动
                startShimmerAnimation()
            }
            itemView.ivShowGallery.layoutParams.height = photoItem.webformatHeight
            itemView.tvDescribe.text = photoItem.user
            itemView.tvLikes.text = photoItem.likes.toString()
            itemView.tvFavorite.text = photoItem.favorites.toString()
            //加载图片
            Glide.with(itemView.context).load(photoItem.webformatURL)
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
                        return false.also { itemView.shimmerLayoutCell?.stopShimmerAnimation() }
                    }
                }).into(itemView.ivShowGallery)
        }
    }

    /**
     * 加载更多的布局
     */
    class FooterViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        companion object {
            fun newInstance(parent: ViewGroup): FooterViewHolder {
                val inflate = LayoutInflater.from(parent.context)
                    .inflate(R.layout.gallery_footer_paging, parent, false).also {
                        //设置进度条占满整个屏幕宽度的方法
                        if (it.layoutParams is StaggeredGridLayoutManager.LayoutParams) {
                            (it.layoutParams as StaggeredGridLayoutManager.LayoutParams).isFullSpan =
                                true
                        } else {
                            (it.layoutParams as RecyclerView.LayoutParams).width =
                                LinearLayout.LayoutParams.MATCH_PARENT
                        }
                    }
                return FooterViewHolder(inflate)
            }
        }

        /**
         * 通过网络状态绑定数据
         */
        fun bindWithNetWorkStatus(netWorkStatus: NetWorkStatus?) {
            with(itemView) {
                when (netWorkStatus) {
                    NetWorkStatus.FAILED -> {
                        tvLoadingPaging.text = "网络错误,点击重试！"
                        progressBarPaging.visibility = View.GONE
                        isClickable = true
                    }
                    NetWorkStatus.COMPLETED -> {
                        tvLoadingPaging.text = "全部加载完毕！"
                        progressBarPaging.visibility = View.GONE
                        isClickable = false
                    }
                    else -> {
                        tvLoadingPaging.text = "正在加载中……"
                        progressBarPaging.visibility = View.VISIBLE
                        isClickable = false
                    }
                }
            }
        }
    }
}

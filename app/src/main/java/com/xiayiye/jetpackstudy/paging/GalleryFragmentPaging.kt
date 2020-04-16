package com.xiayiye.jetpackstudy.paging


import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.xiayiye.jetpackstudy.R
import kotlinx.android.synthetic.main.fragment_gallery.*

/**
 * 使用paging实现画廊加载更多
 */
class GalleryFragmentPaging : Fragment() {
    private val galleryViewModelPaging by viewModels<GalleryViewModelPaging>()
    //切换图片布局的标识
    private var isLin = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_gallery, container, false)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.refresh_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            //刷新数据
            R.id.refreshImage -> {
                //显示刷新控件
                swipeLayoutGallery.isRefreshing = true
                //刷新数据
                galleryViewModelPaging.resetQuery()
            }
            //切换布局
            R.id.changeLayout -> {
                if (isLin) {
                    recyclerViewGallery.layoutManager =
                        StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
                } else {
                    recyclerViewGallery.layoutManager =
                        LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false)
                }
                isLin = !isLin
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        //设置有菜单选项
        setHasOptionsMenu(true)
        val galleryAdapterPaging = GalleryAdapterPaging()
        recyclerViewGallery.apply {
            adapter = galleryAdapterPaging
            //瀑布流式风格的画廊
            layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        }
        galleryViewModelPaging.pageListLiveData.observe(
            viewLifecycleOwner,
            Observer {
                galleryAdapterPaging.submitList(it)
                //加载完成关闭刷新图标
                swipeLayoutGallery.isRefreshing = false
            })
        //下拉刷新
        swipeLayoutGallery.setOnRefreshListener {
            //刷新数据
            galleryViewModelPaging.resetQuery()
        }
    }
}



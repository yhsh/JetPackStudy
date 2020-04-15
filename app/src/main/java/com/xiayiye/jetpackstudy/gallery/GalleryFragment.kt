package com.xiayiye.jetpackstudy.gallery


import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.SavedStateViewModelFactory
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.xiayiye.jetpackstudy.R
import kotlinx.android.synthetic.main.fragment_gallery.*

/**
 * 画廊加载显示图片页面
 */
class GalleryFragment : Fragment() {
    //切换图片布局的标识
    private var isLin = true
    private lateinit var galleryViewModel: GalleryViewModel
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
                galleryViewModel.resetQuery()
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
        val galleryAdapter = GalleryAdapter()
        recyclerViewGallery.apply {
            adapter = galleryAdapter
            //瀑布流式风格的画廊
            layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        }
        galleryViewModel = ViewModelProvider(
            this,
            //方法一
            SavedStateViewModelFactory(requireActivity().application, requireActivity())
            //方法二
            //     ViewModelProvider.AndroidViewModelFactory(requireActivity().application)
        ).get(GalleryViewModel::class.java)
        galleryViewModel.photoListView.observe(this, Observer {
            if (galleryViewModel.needToScrollerToTop) {
                //自动滚动到顶部
                recyclerViewGallery.scrollToPosition(0)
                galleryViewModel.needToScrollerToTop = false
            }
            galleryAdapter.submitList(it)
            //停止刷新控件
            swipeLayoutGallery.isRefreshing = false
        })
        //下拉刷新数据
        swipeLayoutGallery.setOnRefreshListener { galleryViewModel.resetQuery() }
        //监听滑动事件
        recyclerViewGallery.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (dy < 0) return
                if (isLin) {
                    //瀑布流布局
                    val manager = recyclerViewGallery.layoutManager as StaggeredGridLayoutManager
                    val intArray = IntArray(2)
                    manager.findLastVisibleItemPositions(intArray)
                    if (intArray[0] == galleryAdapter.itemCount - 1) {
                        //证明到底了
                        galleryViewModel.fetchData()
                    }
                }
            }

            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
            }
        })
    }
}

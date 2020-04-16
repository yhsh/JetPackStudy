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
import com.xiayiye.jetpackstudy.gallery.Constant.Companion.DATA_STATUS_NETWORK_ERROR
import com.xiayiye.jetpackstudy.gallery.Constant.Companion.FOOT_VIEW_STATUS
import kotlinx.android.synthetic.main.fragment_gallery.*

/**
 * 画廊加载显示图片页面
 */
class GalleryFragment : Fragment() {
    //切换图片布局的标识
    private var isLinearLayout = false
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
                isLinearLayout = !isLinearLayout
                //切换布局的方法
                changeLayoutFun()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        //设置有菜单选项
        setHasOptionsMenu(true)
        galleryViewModel = ViewModelProvider(
            this,
            //方法一
            SavedStateViewModelFactory(requireActivity().application, requireActivity())
            //方法二
            //     ViewModelProvider.AndroidViewModelFactory(requireActivity().application)
        ).get(GalleryViewModel::class.java)
        val galleryAdapter = GalleryAdapter(galleryViewModel)
        recyclerViewGallery.apply {
            adapter = galleryAdapter
            changeLayoutFun()
        }
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
        galleryViewModel.dataStatusLive.observe(this, Observer {
            //记录此时的状态
            FOOT_VIEW_STATUS = it
            //告诉最后一个item需要刷新数据
            galleryAdapter.notifyItemChanged(galleryAdapter.itemCount - 1)
            if (it == DATA_STATUS_NETWORK_ERROR) {
                //网络错误,刷新按钮停止
                swipeLayoutGallery.isRefreshing = false
            }
        })
        //下拉刷新数据
        swipeLayoutGallery.setOnRefreshListener { galleryViewModel.resetQuery() }
        //监听滑动事件
        recyclerViewGallery.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (dy < 0) return
                if (!isLinearLayout) {
                    //瀑布流布局
                    val manager = recyclerViewGallery.layoutManager as StaggeredGridLayoutManager
                    val intArray = IntArray(2)
                    manager.findLastVisibleItemPositions(intArray)
                    if (intArray[0] == galleryAdapter.itemCount - 1) {
                        //证明到底了
                        galleryViewModel.fetchData()
                    }
                } else {
                    val manager = recyclerViewGallery.layoutManager as LinearLayoutManager
                    if (manager.findLastVisibleItemPosition() == galleryAdapter.itemCount - 1) {
                        //证明滑动到底部了
                        galleryViewModel.fetchData()
                    }
                }
            }

            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
            }
        })
    }

    /**
     * 切换布局的方法
     */
    private fun changeLayoutFun() {
        if (isLinearLayout) {
            recyclerViewGallery.layoutManager =
                LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false)
        } else {
            recyclerViewGallery.layoutManager =
                StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        }
    }
}

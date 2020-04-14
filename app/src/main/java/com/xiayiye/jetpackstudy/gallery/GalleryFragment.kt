package com.xiayiye.jetpackstudy.gallery


import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.SavedStateViewModelFactory
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.xiayiye.jetpackstudy.R
import kotlinx.android.synthetic.main.fragment_gallery.*

/**
 * 画廊加载显示图片页面
 */
class GalleryFragment : Fragment() {
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
                galleryViewModel.fetchData()
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
            layoutManager = GridLayoutManager(requireContext(), 2)
        }
        galleryViewModel = ViewModelProvider(
            this,
            //方法一
            SavedStateViewModelFactory(requireActivity().application, requireActivity())
            //方法二
            //     ViewModelProvider.AndroidViewModelFactory(requireActivity().application)
        ).get(GalleryViewModel::class.java)
        galleryViewModel.photoListView.observe(this, Observer {
            galleryAdapter.submitList(it)
            //停止刷新控件
            swipeLayoutGallery.isRefreshing = false
        })
        galleryViewModel.photoListView.value ?: galleryViewModel.fetchData()
        //下拉刷新数据
        swipeLayoutGallery.setOnRefreshListener { galleryViewModel.fetchData() }
    }
}

package com.xiayiye.jetpackstudy.gallery


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.xiayiye.jetpackstudy.R
import kotlinx.android.synthetic.main.fragment_view_pager2_image.*

/**
 * A simple [Fragment] subclass.
 */
class ViewPager2ImageFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_view_pager2_image, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val photoList = arguments?.getParcelableArrayList<PhotoItem>("photo_list")
        val currentPosition = arguments?.getInt("photo_position", 0)
        PagerPhotoListAdapter().apply {
            vp2Banner.adapter = this
            submitList(photoList)
        }
        //设置轮播图片后的滑动当前页
        vp2Banner.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                tvShowImagePage.text =
                    StringBuffer().append(position + 1).append("/").append(photoList?.size)
            }
        })
        //设置 ViewPager2 的当前页要在设置 ViewPager2 的数据后在设置当前页面,否则不生效
        vp2Banner.setCurrentItem(currentPosition ?: 0, false)
    }
}

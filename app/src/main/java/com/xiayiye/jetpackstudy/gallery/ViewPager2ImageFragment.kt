package com.xiayiye.jetpackstudy.gallery


import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
import androidx.core.view.get
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.xiayiye.jetpackstudy.R
import kotlinx.android.synthetic.main.fragment_view_pager2_image.*
import kotlinx.android.synthetic.main.pager_photo_view.view.*

/**
 * A simple [Fragment] subclass.
 */
const val REQUEST_WRITE_EXTERNAL_STORAGE_CODE = 1000

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
        //保存图片的方法
        ivSaveImg.setOnClickListener {
            if (Build.VERSION.SDK_INT < 29 && ContextCompat.checkSelfPermission(
                    requireContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                requestPermissions(
                    arrayOf<String>(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                    REQUEST_WRITE_EXTERNAL_STORAGE_CODE
                )
            } else {
                saveImage()
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_WRITE_EXTERNAL_STORAGE_CODE && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            //权限申请成功可以保存图片
            saveImage()
        }
    }

    /**
     * 保存图片到相册的方法
     *  API29 后此方法已废弃
     */
    private fun saveImage() {
        val holder =
            (vp2Banner[0] as RecyclerView).findViewHolderForAdapterPosition(vp2Banner.currentItem) as PagerPhotoListAdapter.PagerPhotoViewHolder
        val toBitmap = holder.itemView.ivPagerView.drawable.toBitmap()
        val insertImage = MediaStore.Images.Media.insertImage(
            requireActivity().contentResolver, toBitmap, "壁纸", "搜索猫相关图片后保存的图片"
        )
        if (insertImage.isNotEmpty()) {
            Toast.makeText(requireActivity(), "图片保存成功！-${insertImage}", Toast.LENGTH_SHORT).show()
        }
    }
}

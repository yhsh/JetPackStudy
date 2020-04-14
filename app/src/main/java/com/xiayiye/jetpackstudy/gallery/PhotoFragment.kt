package com.xiayiye.jetpackstudy.gallery


import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.xiayiye.jetpackstudy.R
import kotlinx.android.synthetic.main.fragment_photo.*

/**
 * A simple [Fragment] subclass.
 */
class PhotoFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_photo, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        shimmerLayoutLargeImage.apply {
            setShimmerColor(0x55ffffff)
            //闪动的角度
            setShimmerAngle(0)
            //开始闪动
            startShimmerAnimation()
        }
        val data: PhotoItem = arguments?.getParcelable("photo")!!
        Glide.with(requireContext()).load(data.largeImageURL)
            .placeholder(R.drawable.ic_photo_actual)
            .listener(object : RequestListener<Drawable> {
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Drawable>?,
                    isFirstResource: Boolean
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
                    return false.also { shimmerLayoutLargeImage?.stopShimmerAnimation() }
                }

            }).into(ivPhotoView)
    }
}

package com.xiayiye.jetpackstudy.viewpager2


import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.xiayiye.jetpackstudy.R
import kotlinx.android.synthetic.main.fragment_scale_view_pager2.*

/**
 * A simple [Fragment] subclass.
 */
class ScaleViewPager2Fragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_scale_view_pager2, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        ivViewPager2Scale.setOnClickListener {
            val objectAnimatorX = ObjectAnimator.ofFloat(it, "scaleX", 0f, 0f)
            val objectAnimatorY = ObjectAnimator.ofFloat(it, "scaleY", 0f, 0f)
            if (!objectAnimatorX.isRunning) {
                objectAnimatorX.duration = 500
                objectAnimatorY.duration = 500
                objectAnimatorX.setFloatValues(
                    ivViewPager2Scale.scaleX, ivViewPager2Scale.scaleX + 0.2f
                )
                objectAnimatorY.setFloatValues(
                    ivViewPager2Scale.scaleY, ivViewPager2Scale.scaleY + 0.2f
                )
                objectAnimatorX.start()
                objectAnimatorY.start()
            }
        }
    }
}

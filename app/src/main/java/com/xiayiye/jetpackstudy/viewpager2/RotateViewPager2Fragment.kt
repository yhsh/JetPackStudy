package com.xiayiye.jetpackstudy.viewpager2


import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.xiayiye.jetpackstudy.R
import kotlinx.android.synthetic.main.fragment_rotate_view_pager2.*

/**
 * A simple [Fragment] subclass.
 */
class RotateViewPager2Fragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_rotate_view_pager2, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        ivViewPager2Rotate.setOnClickListener {
            val objectAnimator = ObjectAnimator.ofFloat(ivViewPager2Rotate, "rotation", 0f, 0f)
            if (!objectAnimator.isRunning) {
                objectAnimator.duration = 500
                objectAnimator.setFloatValues(
                    ivViewPager2Rotate.rotation,
                    ivViewPager2Rotate.rotation + 50
                )
                objectAnimator.start()
            }
        }
    }
}

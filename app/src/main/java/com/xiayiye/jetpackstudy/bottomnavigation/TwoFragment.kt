package com.xiayiye.jetpackstudy.bottomnavigation

import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.xiayiye.jetpackstudy.R
import kotlinx.android.synthetic.main.two_fragment.*

/**
 * 缩放动画页面
 */
class TwoFragment : Fragment() {

    companion object {
        fun newInstance() = TwoFragment()
    }

    private lateinit var viewModel: TwoViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.two_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(requireActivity()).get(TwoViewModel::class.java)
        ivTwo.scaleX = viewModel.scaleFloat
        ivTwo.scaleY = viewModel.scaleFloat
        val objectAnimatorX = ObjectAnimator.ofFloat(ivTwo, "scaleX", 0f, 0f)
        val objectAnimatorY = ObjectAnimator.ofFloat(ivTwo, "scaleY", 0f, 0f)
        objectAnimatorX.duration = 500
        objectAnimatorY.duration = 500
        ivTwo.setOnClickListener {
            if (!objectAnimatorX.isRunning) {
                //设置每次缩放的大小😂
                objectAnimatorX.setFloatValues(ivTwo.scaleX + 0.1f)
                objectAnimatorY.setFloatValues(ivTwo.scaleY + 0.1f)
                viewModel.scaleFloat += 0.1f
                objectAnimatorX.start()
                objectAnimatorY.start()
            }
        }
    }

}

package com.xiayiye.jetpackstudy.bottomnavigation

import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.xiayiye.jetpackstudy.R
import kotlinx.android.synthetic.main.three_fragment.*
import java.util.*

/**
 * 平移动画页面
 */
class ThreeFragment : Fragment() {

    companion object {
        fun newInstance() = ThreeFragment()
    }

    private lateinit var viewModel: ThreeViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.three_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(requireActivity()).get(ThreeViewModel::class.java)
        val objectAnimator = ObjectAnimator.ofFloat(ivThree, "x", 0f, 0f)
        objectAnimator.duration = 500
        ivThree.x = viewModel.spaceX
        ivThree.setOnClickListener {
            if (!objectAnimator.isRunning) {
                val space: Float = if (Random().nextBoolean()) {
                    100f
                } else {
                    -100f
                }
                objectAnimator.setFloatValues(ivThree.x, ivThree.x + space)
                viewModel.spaceX += space
                objectAnimator.start()
            }
        }
    }

}

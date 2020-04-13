package com.xiayiye.jetpackstudy.bottomnavigation

import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.xiayiye.jetpackstudy.R
import kotlinx.android.synthetic.main.first_fragment.*

/**
 * 旋转动画页面
 */
class FirstFragment : Fragment() {

    companion object {
        fun newInstance() = FirstFragment()
    }

    private lateinit var viewModel: FirstViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.first_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        //使用requireActivity()可以将生命周期管理延长到Activity不仅限于fragment中记录度数值
        viewModel = ViewModelProvider(requireActivity()).get(FirstViewModel::class.java)
        //设置记录的值
        ivFirst.rotation = viewModel.rotationPosition
        val objectAnimator: ObjectAnimator = ObjectAnimator.ofFloat(ivFirst, "rotation", 0f, 0f)
        objectAnimator.duration = 500
        ivFirst.setOnClickListener {
            if (!objectAnimator.isRunning) {
                //正在运行就不开始动画
                objectAnimator.setFloatValues(
                    ivFirst.rotation,
                    ivFirst.rotation + 100
                )
                viewModel.rotationPosition += 100
                objectAnimator.start()
            }
        }
    }

}

package com.xiayiye.jetpackstudy.viewpager2


import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.xiayiye.jetpackstudy.R
import kotlinx.android.synthetic.main.fragment_translate_view_pager2.*
import java.util.*

/**
 * A simple [Fragment] subclass.
 */
class TranslateViewPager2Fragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_translate_view_pager2, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        var space: Int
        ivVp2Translate.setOnClickListener {
            if (Random().nextBoolean()) {
                space = Random().nextInt(100)
            } else {
                space = Random().nextInt(100) * -1
            }
            val objectAnimator = ObjectAnimator.ofFloat(ivVp2Translate, "y", 0f, 0f)
            if (!objectAnimator.isRunning) {
                objectAnimator.duration = 500
                objectAnimator.setFloatValues(
                    ivVp2Translate.y,
                    ivVp2Translate.y + space
                )
                objectAnimator.start()
            }
        }
    }
}

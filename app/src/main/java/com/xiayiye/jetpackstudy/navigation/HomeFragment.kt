package com.xiayiye.jetpackstudy.navigation


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.xiayiye.jetpackstudy.R
import kotlinx.android.synthetic.main.fragment_home.*

/**
 * A simple [Fragment] subclass.
 */
class HomeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        btGoDetail.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.action_homeFragment_to_detailFragment))
        //方法二
//        btGoDetail.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.detailFragment))
    }
}

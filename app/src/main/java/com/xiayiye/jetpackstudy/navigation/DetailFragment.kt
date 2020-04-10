package com.xiayiye.jetpackstudy.navigation


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation

import com.xiayiye.jetpackstudy.R
import kotlinx.android.synthetic.main.fragment_detail.*

/**
 * A simple [Fragment] subclass.
 */
class DetailFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_detail, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        //拿到上页传来的参数信息进行显示
        val name = arguments?.getString("name")
        tvShowResultArgs.text = name
        btGoIndex.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.action_detailFragment_to_homeFragment)
            //或者下面写法也可以
//            Navigation.findNavController(it).navigate(R.id.homeFragment)
        }
    }

}

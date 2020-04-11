package com.xiayiye.jetpackstudy.navigation.viewmodel

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.xiayiye.jetpackstudy.R
import com.xiayiye.jetpackstudy.databinding.FragmentDetailsBinding

class DetailsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //下面必须使用activity!!否则masterViewModel数据无法共享
        val masterViewModel = ViewModelProvider(activity!!).get(MasterViewModel::class.java)
        val detailsBinding = DataBindingUtil.inflate<FragmentDetailsBinding>(
            inflater,
            R.layout.fragment_details,
            container,
            false
        )
        //必须要设置下面的绑定数据才能生效
        detailsBinding.masterViewModel = masterViewModel
        detailsBinding.lifecycleOwner = activity
        detailsBinding.btGoIndexPage.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.action_detailsFragment_to_masterFragment)
        }
        return detailsBinding.root
    }
}

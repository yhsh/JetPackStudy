package com.xiayiye.jetpackstudy.calculation


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.SavedStateViewModelFactory
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.xiayiye.jetpackstudy.R
import com.xiayiye.jetpackstudy.databinding.FragmentWinBinding

/**
 * A simple [Fragment] subclass.
 */
class WinFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        /**
         * requireActivity()和getActivity()是一样的效果,此处不能使用this,
         * 必须使用activity相关的,否则几个Fragment之间不能共享数据
         */
        val calculationViewModel = ViewModelProvider(
            requireActivity(),
            SavedStateViewModelFactory(requireActivity().application, requireActivity())
        ).get(CalculationViewModel::class.java)
        val fragmentWinBinding = DataBindingUtil.inflate<FragmentWinBinding>(
            inflater,
            R.layout.fragment_win,
            container,
            false
        )
        fragmentWinBinding.calculationData = calculationViewModel
        fragmentWinBinding.lifecycleOwner = requireActivity()
        fragmentWinBinding.btWinBackHome.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.action_winFragment_to_titleFragment)
        }
        return fragmentWinBinding.root
    }
}

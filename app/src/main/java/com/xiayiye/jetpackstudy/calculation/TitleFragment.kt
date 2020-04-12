package com.xiayiye.jetpackstudy.calculation


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.SavedStateViewModelFactory
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.xiayiye.jetpackstudy.R
import com.xiayiye.jetpackstudy.databinding.FragmentTitleBinding

/**
 * A simple [Fragment] subclass.
 */
class TitleFragment : Fragment() {

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
        val fragmentDataBinding =
            DataBindingUtil.inflate<FragmentTitleBinding>(
                inflater, R.layout.fragment_title,
                container, false
            )
        fragmentDataBinding.calculationData = calculationViewModel
        fragmentDataBinding.lifecycleOwner = requireActivity()
        return fragmentDataBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        view?.findViewById<Button>(R.id.btStartCalculation)?.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.action_titleFragment_to_questionFragment)
        }
    }
}

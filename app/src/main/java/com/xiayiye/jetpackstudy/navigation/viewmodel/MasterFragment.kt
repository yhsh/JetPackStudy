package com.xiayiye.jetpackstudy.navigation.viewmodel


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.xiayiye.jetpackstudy.R
import com.xiayiye.jetpackstudy.databinding.FragmentMasterBinding

class MasterFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //下面必须使用activity!!否则masterViewModel数据无法共享
        val masterViewModel: MasterViewModel =
            ViewModelProvider(activity!!).get(MasterViewModel::class.java)
        val masterBinding = DataBindingUtil.inflate<FragmentMasterBinding>(
            inflater,
            R.layout.fragment_master,
            container,
            false
        )
        masterBinding.masterViewModel = masterViewModel
        masterBinding.lifecycleOwner = activity
        //设置默认seekBar的值以及从details页面回传数据的值
        masterBinding.sbShowProgress.progress = masterViewModel.getMasterNumber().value!!
        masterBinding.sbShowProgress.setOnSeekBarChangeListener(object :
            SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(p0: SeekBar?, progress: Int, p2: Boolean) {
                masterViewModel.getMasterNumber().value = progress
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {

            }

            override fun onStopTrackingTouch(p0: SeekBar?) {

            }

        })
        masterBinding.btGoStep.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.action_masterFragment_to_detailsFragment)
        }
        return masterBinding.root
    }
}

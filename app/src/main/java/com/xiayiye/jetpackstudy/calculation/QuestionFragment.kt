package com.xiayiye.jetpackstudy.calculation


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.SavedStateViewModelFactory
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.xiayiye.jetpackstudy.R
import com.xiayiye.jetpackstudy.databinding.FragmentQuestionBinding

/**
 * A simple [Fragment] subclass.
 */
class QuestionFragment : Fragment(), View.OnClickListener {
    val sb = StringBuffer()
    private lateinit var fragmentQuestionBinding: FragmentQuestionBinding
    private lateinit var calculationViewModel: CalculationViewModel
    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.button13 -> sb.append("1")
            R.id.button14 -> sb.append("2")
            R.id.button15 -> sb.append("3")
            R.id.button16 -> sb.append("4")
            R.id.button17 -> sb.append("5")
            R.id.button18 -> sb.append("6")
            R.id.button19 -> sb.append("7")
            R.id.button20 -> sb.append("8")
            R.id.button21 -> sb.append("9")
            //清空数据
            R.id.button22 -> sb.setLength(0)
            R.id.button23 -> sb.append("0")
        }
        if (sb.isEmpty()) {
            fragmentQuestionBinding.textView16.text =
                requireActivity().getString(R.string.you_answer)
        } else {
            fragmentQuestionBinding.textView16.text = sb.toString()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        /**
         * requireActivity()和getActivity()是一样的效果,此处不能使用this,
         * 必须使用activity相关的,否则几个Fragment之间不能共享数据
         */
        calculationViewModel = ViewModelProvider(
            requireActivity(),
            SavedStateViewModelFactory(requireActivity().application, requireActivity())
        ).get(CalculationViewModel::class.java)
        //初始化题目
        calculationViewModel.generator()
        fragmentQuestionBinding = DataBindingUtil.inflate<FragmentQuestionBinding>(
            inflater,
            R.layout.fragment_question,
            container,
            false
        )
        fragmentQuestionBinding.calculationData = calculationViewModel
        fragmentQuestionBinding.lifecycleOwner = requireActivity()
        fragmentQuestionBinding.button13.setOnClickListener(this)
        fragmentQuestionBinding.button14.setOnClickListener(this)
        fragmentQuestionBinding.button15.setOnClickListener(this)
        fragmentQuestionBinding.button16.setOnClickListener(this)
        fragmentQuestionBinding.button17.setOnClickListener(this)
        fragmentQuestionBinding.button18.setOnClickListener(this)
        fragmentQuestionBinding.button19.setOnClickListener(this)
        fragmentQuestionBinding.button20.setOnClickListener(this)
        fragmentQuestionBinding.button21.setOnClickListener(this)
        fragmentQuestionBinding.button22.setOnClickListener(this)
        fragmentQuestionBinding.button23.setOnClickListener(this)
        return fragmentQuestionBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        view?.findViewById<Button>(R.id.button24)?.setOnClickListener {
            if (sb.isEmpty()) {
                sb.append("-1")
            }
            if (sb.toString().toInt() == calculationViewModel.getAnswer.value) {
                //如果答案相同,继续答题
                calculationViewModel.answerCorrect()
                sb.setLength(0)
                view?.findViewById<TextView>(R.id.textView16)?.text =
                    requireActivity().getString(R.string.answer_correct_message)
            } else {
                if (calculationViewModel.winFlag) {
                    Navigation.findNavController(it)
                        .navigate(R.id.action_questionFragment_to_winFragment)
                    //修改状态
                    calculationViewModel.winFlag = false
                    //保存记录
                    calculationViewModel.save()
                } else {
                    Navigation.findNavController(it)
                        .navigate(R.id.action_questionFragment_to_loseFragment)
                }
            }
        }
    }
}

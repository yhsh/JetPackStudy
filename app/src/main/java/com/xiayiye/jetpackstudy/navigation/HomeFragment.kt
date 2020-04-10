package com.xiayiye.jetpackstudy.navigation


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
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
//        btGoDetail.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.action_homeFragment_to_detailFragment))
        //方法二
        btGoDetail.setOnClickListener {
            val args = etInputArgs.text.toString().trim()
            if (args.isNotEmpty()) {
                val bundle = Bundle()
                bundle.putString("name", args)
                //传递参数的方法
                Navigation.findNavController(it).navigate(R.id.detailFragment, bundle)
            } else {
                Toast.makeText(activity, "参数不能为空", Toast.LENGTH_SHORT).show()
            }
        }
    }
}

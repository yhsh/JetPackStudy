package com.xiayiye.jetpackstudy.navigationdrawer


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.xiayiye.jetpackstudy.R
import kotlinx.android.synthetic.main.drawer_content_layout.*

/**
 * A simple [Fragment] subclass.
 */
class TextFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        requireActivity().collapsingToolbarLayout.title = getString(R.string.fragment_text)
        requireActivity().toolbarIcon.setImageResource(R.drawable.ic_looks_one_black_50dp)
        return inflater.inflate(R.layout.fragment_text, container, false)
    }
}

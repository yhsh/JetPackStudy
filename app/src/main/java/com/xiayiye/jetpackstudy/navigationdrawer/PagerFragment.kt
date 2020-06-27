package com.xiayiye.jetpackstudy.navigationdrawer


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.xiayiye.jetpackstudy.R
import kotlinx.android.synthetic.main.drawer_content_layout.*
import kotlinx.android.synthetic.main.fragment_pager.*

/**
 * A simple [Fragment] subclass.
 */
class PagerFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        requireActivity().collapsingToolbarLayout.title = getString(R.string.fragment_pager)
        requireActivity().toolbarIcon.setImageResource(R.drawable.ic_looks_two_black_50dp)
        return inflater.inflate(R.layout.fragment_pager, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val viewPager2Adapter = MyListAdapter(true)
        viewPager2Drawer.adapter = viewPager2Adapter
        viewPager2Adapter.submitList(ImageListUtils.getImgData())
    }
}

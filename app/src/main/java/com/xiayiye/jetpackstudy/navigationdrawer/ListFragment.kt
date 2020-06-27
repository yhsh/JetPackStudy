package com.xiayiye.jetpackstudy.navigationdrawer


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.xiayiye.jetpackstudy.R
import kotlinx.android.synthetic.main.drawer_content_layout.*
import kotlinx.android.synthetic.main.fragment_list.*

/**
 * A simple [Fragment] subclass.
 */
class ListFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        requireActivity().collapsingToolbarLayout.title = getString(R.string.fragment_list)
        requireActivity().toolbarIcon.setImageResource(R.drawable.ic_looks_three_black_50dp)
        return inflater.inflate(R.layout.fragment_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val myListAdapter = MyListAdapter(false)
        val gridLayoutManager = GridLayoutManager(requireContext(), 2)
        rvDrawerRecyclerView.apply {
            layoutManager = gridLayoutManager
            adapter = myListAdapter
        }
        myListAdapter.submitList(ImageListUtils.getImgData())
    }
}

package com.xiayiye.jetpackstudy.paging

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.Navigation
import androidx.navigation.ui.setupActionBarWithNavController
import com.xiayiye.jetpackstudy.R

/**
 * 使用Paging加载更多
 */
class PagingLoadMoreActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_paging_load_more)
        setupActionBarWithNavController(
            Navigation.findNavController(this, R.id.fragmentGalleryLoadMore)
        )
    }

    override fun onSupportNavigateUp(): Boolean {
        return Navigation.findNavController(this, R.id.fragmentGalleryLoadMore).navigateUp()
    }
}

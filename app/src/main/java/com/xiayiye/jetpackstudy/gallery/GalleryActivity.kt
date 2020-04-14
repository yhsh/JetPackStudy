package com.xiayiye.jetpackstudy.gallery

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.Navigation
import androidx.navigation.ui.setupActionBarWithNavController
import com.xiayiye.jetpackstudy.R

class GalleryActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gallery)
        setupActionBarWithNavController(Navigation.findNavController(this, R.id.fragmentGallery))
    }

    override fun onSupportNavigateUp(): Boolean {
        return Navigation.findNavController(this, R.id.fragmentGallery).navigateUp()
    }
}

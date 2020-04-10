package com.xiayiye.jetpackstudy.savestate

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.SavedStateViewModelFactory
import androidx.lifecycle.ViewModelProvider
import com.xiayiye.jetpackstudy.R
import com.xiayiye.jetpackstudy.databinding.ActivitySaveStateBinding


/**
 * 可让APP被后台杀死后恢复的页面
 */
class SaveStateActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val saveStateViewModel = ViewModelProvider(
            this, SavedStateViewModelFactory(application, this)
        ).get(SaveStateViewModel::class.java)
        val saveStateBinding = DataBindingUtil.setContentView<ActivitySaveStateBinding>(
            this,
            R.layout.activity_save_state
        )
        saveStateBinding.saveStateData = saveStateViewModel
        saveStateBinding.lifecycleOwner = this
    }
}

package com.podium.papermenu

import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowInsets
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.podium.papermenu.databinding.ActivityFullscreenBinding

class FullscreenActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFullscreenBinding
    private var isFullscreen: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFullscreenBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        isFullscreen = true
    }


}
package com.dicoding.bukuku

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.dicoding.bukuku.databinding.ActivityLibraryBinding

class LibraryActivity : AppCompatActivity() {

    private val binding: ActivityLibraryBinding by lazy {
        ActivityLibraryBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }
}
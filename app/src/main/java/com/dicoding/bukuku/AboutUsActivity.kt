package com.dicoding.bukuku

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.dicoding.bukuku.databinding.ActivityAboutUsBinding

class AboutUsActivity : AppCompatActivity() {

    private val binding: ActivityAboutUsBinding by lazy {
        ActivityAboutUsBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        supportActionBar?.hide()

        binding.imgArrowBack.setOnClickListener {
            onBackPressed()
            finish()
        }
    }
}
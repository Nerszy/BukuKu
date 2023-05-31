package com.dicoding.bukuku

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import com.dicoding.bukuku.databinding.ActivityMainBinding
import com.google.android.material.search.SearchBar

class MainActivity : AppCompatActivity() {

    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.svSearch.setOnQueryTextFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                startActivity(Intent(this, SearchActivity::class.java))
                binding.svSearch.clearFocus()
            }
        }

    }
}
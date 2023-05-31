package com.dicoding.bukuku

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.OnBackPressedCallback
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.bukuku.databinding.ActivitySearchBinding

class SearchActivity : AppCompatActivity() {

    private val binding: ActivitySearchBinding by lazy {
        ActivitySearchBinding.inflate(layoutInflater)
    }

    private val adapter: SearchBookAdapter by lazy {
        SearchBookAdapter()
    }

    private val onBackPressedCallback: OnBackPressedCallback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            finish()
        }
    }

    private lateinit var mSearch: SearchViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        onBackPressedDispatcher.addCallback(this, onBackPressedCallback)
        setContentView(binding.root)

        binding.rvBook.layoutManager = GridLayoutManager(this, 2)
        binding.rvBook.setHasFixedSize(true)
        binding.rvBook.adapter = adapter

        mSearch = ViewModelProvider(this)[SearchViewModel::class.java]

        mSearch.setBook()
        mSearch.listBook.observe(this) { bookList ->
            if (bookList != null) {
                adapter.setBooks(bookList)
                adapter.notifyDataSetChanged()
            }
        }
    }
}

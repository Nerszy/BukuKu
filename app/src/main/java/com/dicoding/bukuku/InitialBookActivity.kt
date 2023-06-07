package com.dicoding.bukuku

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.bukuku.databinding.ActivityInitialBookBinding
import com.dicoding.bukuku.response.BooksItem
import java.util.*
import kotlin.collections.ArrayList

class InitialBookActivity : AppCompatActivity() {

    private val binding: ActivityInitialBookBinding by lazy {
        ActivityInitialBookBinding.inflate(layoutInflater)
    }

    private val mBookByGenre: InitialBookViewModel by viewModels()

    private lateinit var genres: List<String>
    private val adapters: MutableList<InitialBookAdapter> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        supportActionBar?.hide()
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN

        genres = intent.getStringArrayListExtra(EXTRA_GENRES) ?: emptyList() // Mendapatkan daftar genre dari Intent

        val layoutManagers = List(genres.size) { index ->
            if (index == 2) {
                LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, true)
            } else {
                LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
            }
        }

        binding.rvGenre1.layoutManager = layoutManagers.getOrNull(0)
        binding.rvGenre2.layoutManager = layoutManagers.getOrNull(1)
        binding.rvGenre3.layoutManager = layoutManagers.getOrNull(2)

        for ((index, genre) in genres.withIndex()) {
            val adapter = InitialBookAdapter()
            val recyclerView: RecyclerView = when (index) {
                0 -> binding.rvGenre1
                1 -> binding.rvGenre2
                2 -> binding.rvGenre3
                else -> throw IllegalStateException("Invalid index for genre")
            }
            recyclerView.adapter = adapter
            adapters.add(adapter)

            val lowercaseGenre = genre.lowercase(Locale.getDefault())

            mBookByGenre.getBooksByGenre(lowercaseGenre)

            val genreLiveData = mBookByGenre.listBooksMap[lowercaseGenre]
            genreLiveData?.observe(this) { books ->
                adapter.updateBooks(books)
                Log.d("InitialBookActivity", "books: $books")
            }
        }

        binding.tvNext.setOnClickListener {
            val selectedBooks = getSelectedBooks()
            Toast.makeText(this, selectedBooks, Toast.LENGTH_SHORT).show()
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }

    private fun getSelectedBooks(): String {
        val selectedBooks = adapters.flatMap { adapter ->
            adapter.getSelectedBookIds()
        }
        return "Selected Books: ${selectedBooks.joinToString(",")}"
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    companion object {
        const val EXTRA_GENRES = "extra_genres"
    }
}

package com.dicoding.bukuku.initialbook

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.bukuku.MainActivity
import com.dicoding.bukuku.databinding.ActivityInitialBookBinding
import java.util.*

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

        genres = intent.getStringArrayListExtra(EXTRA_GENRES)
            ?: emptyList() // Mendapatkan daftar genre dari Intent

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
            }
        }

        mBookByGenre.errorState.observe(this) { isError ->
            if (isError) {
                Toast.makeText(this, "An error occurred", Toast.LENGTH_SHORT).show()
            }
        }

        mBookByGenre.loadingState.observe(this) { isLoading ->
            showLoading(isLoading)
        }

        binding.tvNext.setOnClickListener { onNextButtonClick() }
    }

    private fun onNextButtonClick() {
        val selectedBooks = getSelectedBooks()
        Toast.makeText(this, listOf(selectedBooks).toString(), Toast.LENGTH_SHORT).show()
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

    private fun getSelectedBooks(): List<Int> {
        val selectedBooks = adapters.flatMap { adapter ->
            adapter.getSelectedBookIds()
        }
        return selectedBooks
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        binding.tvNext.isEnabled = !isLoading // Disable tombol Next saat sedang loading
        if (isLoading) {
            binding.tvNext.setOnClickListener(null) // Jangan beri action saat loading
        } else {
            binding.tvNext.setOnClickListener { onNextButtonClick() } // Beri action saat tidak loading
        }
    }

    companion object {
        const val EXTRA_GENRES = "extra_genres"
    }
}


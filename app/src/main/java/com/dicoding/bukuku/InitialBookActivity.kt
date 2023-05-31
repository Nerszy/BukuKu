package com.dicoding.bukuku

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.bukuku.databinding.ActivityInitialBookBinding

@Suppress("DEPRECATION")
class InitialBookActivity : AppCompatActivity() {

    private val genres = listOf(
        "Action",
        "Horror",
        "Comedy"
    )

    private val binding: ActivityInitialBookBinding by lazy {
        ActivityInitialBookBinding.inflate(layoutInflater)
    }

    private val adapters: List<InitialBookAdapter> by lazy {
        genres.map { genre -> InitialBookAdapter(getBooksByGenre(genre)) }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        supportActionBar?.hide()
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN

        val layoutManagers = genres.mapIndexed { index, _ ->
            if (index == 2) {
                LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, true)
            } else {
                LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
            }
        }

        binding.rvGenre1.layoutManager = layoutManagers[0]
        binding.rvGenre2.layoutManager = layoutManagers[1]
        binding.rvGenre3.layoutManager = layoutManagers[2]

        binding.rvGenre1.adapter = adapters[0]
        binding.rvGenre2.adapter = adapters[1]
        binding.rvGenre3.adapter = adapters[2]

        binding.tvNext.setOnClickListener {
            val selectedBooks = getSelectedBooks()
            Toast.makeText(this, selectedBooks, Toast.LENGTH_SHORT).show()
            startActivity(Intent(this, MainActivity::class.java))
        }
    }

    private fun getBooksByGenre(genre: String): List<String> {
        // Buat daftar judul buku berdasarkan genre
        val books: List<String> = when (genre) {
            "Action" -> listOf("Book 1", "Book 2", "Book 3", "Book 4", "Book 5", "Book 6", "Book 7", "Book 8", "Book 9", "Book 10")
            "Horror" -> listOf("Book A", "Book B", "Book C", "Book D", "Book E", "Book F", "Book G", "Book H", "Book I", "Book J")
            "Comedy" -> listOf("Book X", "Book Y", "Book Z", "Book P", "Book Q", "Book R", "Book S", "Book T", "Book U", "Book V")
            else -> emptyList()
        }
        return books
    }

    private fun getSelectedBooks(): String {
        val selectedBooks = adapters.map { adapter ->
            adapter.getSelectedBook()
        }
        return "Selected Books: ${selectedBooks.joinToString(",")}"
    }

}
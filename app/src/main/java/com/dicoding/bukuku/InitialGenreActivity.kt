package com.dicoding.bukuku

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.dicoding.bukuku.databinding.ActivityInitialGenreBinding

@Suppress("DEPRECATION")
class InitialGenreActivity : AppCompatActivity() {

    private val genre = listOf(
        "Action",
        "Adventure",
        "Comedy",
        "Drama",
        "Fantasy",
        "Horror",
        "Mystery",
        "Romance",
        "Thriller",
        "Western",
        "Sci-Fi",
        "Crime",
        "Animation",
        "Family",
        "History",
        "War",
        "Music",
        "Documentary",
        "TV Movie",
        "Foreign",
        "Reality",
        "Soap",
        "Talk",
        "War & Politics",
        "News"
    ).sortedBy { it }

    private val binding: ActivityInitialGenreBinding by lazy {
        ActivityInitialGenreBinding.inflate(layoutInflater)
    }

    private val adapter: GenreAdapter by lazy {
        GenreAdapter(genre)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
        supportActionBar?.hide()

        val layoutManager = StaggeredGridLayoutManager(3,GridLayoutManager.VERTICAL)
        binding.rvGenres.adapter = adapter
        binding.rvGenres.layoutManager = layoutManager

        binding.tvNext.setOnClickListener {
            val selectedGenresText = getSelectedGenresText()
            Toast.makeText(this, selectedGenresText, Toast.LENGTH_SHORT).show()
            startActivity(Intent(this, InitialBookActivity::class.java))
        }
    }

    private fun getSelectedGenresText(): String {
        val selectedGenres = adapter.getSelectedGenre()
        return "Selected Genres: ${selectedGenres.joinToString(", ")}"
    }
}
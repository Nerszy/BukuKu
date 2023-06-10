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
        "Fiction", "Non-Fiction", "Young-Adult", "Fantasy", "Classics", "Historical-Fiction", "Mystery",
        "Book-Club", "Romance", "Nonfiction", "Contemporary", "Thriller", "Science-Fiction", "Childrens",
        "Owned", "Memoir", "Picture-Books", "Biography", "Horror", "History"
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

        val layoutManager = GridLayoutManager(this, 2)
        binding.rvGenres.adapter = adapter
        binding.rvGenres.layoutManager = layoutManager

        binding.tvNext.setOnClickListener {
            val selectedGenres = adapter.getSelectedGenre()

            if (selectedGenres.size == 3) {
                val selectedGenresText = getSelectedGenresText()
                val intent = Intent(this, InitialBookActivity::class.java)
                intent.putStringArrayListExtra(InitialBookActivity.EXTRA_GENRES, ArrayList(selectedGenresText))
                startActivity(intent)
                finish()
            } else {
                Toast.makeText(this, "Please choose 3 genres", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun getSelectedGenresText(): List<String> {
        return adapter.getSelectedGenre()
    }
}

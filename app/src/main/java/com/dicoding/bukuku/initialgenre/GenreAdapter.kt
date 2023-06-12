package com.dicoding.bukuku.initialgenre

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.bukuku.R
import com.dicoding.bukuku.databinding.ItemRowGenreBinding

class GenreAdapter(private val genres: List<String>) :
    RecyclerView.Adapter<GenreAdapter.GenreViewHolder>() {

    private val selectedGenre = mutableListOf<String>()

    inner class GenreViewHolder(val binding: ItemRowGenreBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(genres: String) {
            with(binding) {
                tvGenre.text = genres
                updateSelectionBackground(genres)
            }
        }

        fun updateSelectionBackground(genre: String) {
            if (selectedGenre.contains(genre)) {
                binding.tvGenre.setBackgroundResource(R.drawable.selected_genre_background)
            } else {
                binding.tvGenre.setBackgroundResource(R.drawable.unselected_genre_background)
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): GenreViewHolder {
        val binding =
            ItemRowGenreBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return GenreViewHolder(binding)
    }

    override fun getItemCount(): Int = genres.size

    override fun onBindViewHolder(holder: GenreViewHolder, position: Int) {
        holder.bind(genres[position])

        holder.binding.tvGenre.setOnClickListener {
            val genre = genres[position]

            if (selectedGenre.contains(genre)) {
                selectedGenre.remove(genre)
            }
            else {
                if (selectedGenre.size < 3) {
                    selectedGenre.add(genre)
                }
                else {
                    Toast.makeText(it.context, "Max 3 Genre", Toast.LENGTH_SHORT).show()
                }
            }
            holder.updateSelectionBackground(genre)
        }
    }

    fun getSelectedGenre(): List<String> = selectedGenre
}

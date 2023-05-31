package com.dicoding.bukuku

import android.annotation.SuppressLint
import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.bukuku.databinding.ItemBookRowBinding

class InitialBookAdapter(private val books: List<String>) :
    RecyclerView.Adapter<InitialBookAdapter.BookViewHolder>() {

    private val selectedBook = mutableListOf<String>()

    inner class BookViewHolder(val binding: ItemBookRowBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(book: String) {
            with(binding) {
                imgBook.setImageResource(R.drawable.sample_book)
                tvBook.text = book

                if (selectedBook.contains(book)) {
                    linearBook.setBackgroundResource(R.drawable.selected_book)
                    tvBook.setTypeface(null, Typeface.BOLD)
                } else {
                    linearBook.setBackgroundResource(0)
                    tvBook.setTypeface(null, Typeface.NORMAL)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookViewHolder {
        val binding = ItemBookRowBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return BookViewHolder(binding)
    }

    override fun getItemCount(): Int = books.size

    @SuppressLint("NotifyDataSetChanged")
    override fun onBindViewHolder(holder: BookViewHolder, position: Int) {
        holder.bind(books[position])

        holder.binding.imgBook.setOnClickListener {
            if (selectedBook.contains(books[position])) {
                selectedBook.remove(books[position])
            } else {
                selectedBook.add(books[position])
            }
            notifyDataSetChanged()
        }
    }

    fun getSelectedBook(): List<String> = selectedBook
}

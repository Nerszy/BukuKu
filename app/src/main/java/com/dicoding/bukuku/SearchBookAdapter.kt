package com.dicoding.bukuku

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.bukuku.databinding.ItemBookBigBinding

class SearchBookAdapter : RecyclerView.Adapter<SearchBookAdapter.ViewHolder>() {

    private val books = ArrayList<BooksItem>()

        inner class ViewHolder(val binding: ItemBookBigBinding) : RecyclerView.ViewHolder(binding.root)


        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val binding = ItemBookBigBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return ViewHolder(binding)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val book = books[position]
            with(holder.binding) {
                tvBookTitle.text = book.title
                Glide.with(holder.itemView.context)
                    .load(book.urlImage)
                    .into(imgBook)
            }
        }

        override fun getItemCount(): Int = books.size

        fun setBooks(book: ArrayList<BooksItem>) {
            this.books.clear()
            this.books.addAll(books)
        }

}
package com.dicoding.bukuku

import android.annotation.SuppressLint
import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.dicoding.bukuku.databinding.ItemBookRowBinding
import com.dicoding.bukuku.response.BooksItem

class InitialBookAdapter : RecyclerView.Adapter<InitialBookAdapter.BookViewHolder>() {

    private val books: ArrayList<BooksItem> = ArrayList()
    private val selectedBookIds: ArrayList<String> = ArrayList()

    inner class BookViewHolder(val binding: ItemBookRowBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(book: BooksItem) {
            with(binding) {
                Glide.with(itemView)
                    .load(book.urlImage)
                    .placeholder(R.drawable.sample_book)
                    .centerCrop()
                    .transform(RoundedCorners(15))
                    .into(imgBook)
                tvBook.text = book.title

                if (selectedBookIds.contains(book.id)) {
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

        val book = books[position]
        val isSelected = selectedBookIds.contains(book.id)

        holder.binding.imgBook.setOnClickListener {
            if (isSelected) {
                selectedBookIds.remove(book.id)
            } else {
                selectedBookIds.add(book.id)
            }
            notifyItemChanged(position)
        }
    }

    fun updateBooks(newBooks: ArrayList<BooksItem>) {
        val diffCallback = BookDiffCallback(books, newBooks)
        val diffResult = DiffUtil.calculateDiff(diffCallback)

        books.clear()
        books.addAll(newBooks)
        diffResult.dispatchUpdatesTo(this)
    }

    fun getSelectedBookIds(): ArrayList<String> = selectedBookIds

    private class BookDiffCallback(
        private val oldBooks: ArrayList<BooksItem>,
        private val newBooks: ArrayList<BooksItem>
    ) : DiffUtil.Callback() {
        override fun getOldListSize(): Int = oldBooks.size
        override fun getNewListSize(): Int = newBooks.size
        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldBooks[oldItemPosition].id == newBooks[newItemPosition].id
        }
        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldBooks[oldItemPosition] == newBooks[newItemPosition]
        }
    }
}

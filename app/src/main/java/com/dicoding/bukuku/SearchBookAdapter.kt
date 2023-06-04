package com.dicoding.bukuku

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.dicoding.bukuku.databinding.ItemBookBigBinding
import com.dicoding.bukuku.response.BooksItem

class SearchBookAdapter : RecyclerView.Adapter<SearchBookAdapter.ViewHolder>() {

    private val books = ArrayList<BooksItem>()
    private var onItemClickCallback: OnItemClickCallback? = null

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
                .optionalFitCenter()
                .transform(RoundedCorners(30))
                .into(imgBook)
            root.setOnClickListener {
                onItemClickCallback?.onItemClicked(book)
            }
        }
    }

    override fun getItemCount(): Int = books.size

    fun setBooks(newBooks: ArrayList<BooksItem>) {
        val diffResult = DiffUtil.calculateDiff(BooksDiffCallback(books, newBooks))
        books.clear()
        books.addAll(newBooks)
        diffResult.dispatchUpdatesTo(this)
    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: BooksItem)
    }
}

class BooksDiffCallback(
    private val oldBooks: List<BooksItem>,
    private val newBooks: List<BooksItem>
) : DiffUtil.Callback() {

    override fun getOldListSize(): Int {
        return oldBooks.size
    }

    override fun getNewListSize(): Int {
        return newBooks.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldBook = oldBooks[oldItemPosition]
        val newBook = newBooks[newItemPosition]
        return oldBook.booksId == newBook.booksId
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldBook = oldBooks[oldItemPosition]
        val newBook = newBooks[newItemPosition]
        return oldBook == newBook
    }
}

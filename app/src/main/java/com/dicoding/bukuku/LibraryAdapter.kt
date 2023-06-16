package com.dicoding.bukuku

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.dicoding.bukuku.databinding.ItemBookBigBinding
import com.dicoding.bukuku.response.LibraryItem

class LibraryAdapter: RecyclerView.Adapter<LibraryAdapter.ViewHolder>() {

    private val listLibrary = ArrayList<LibraryItem>()
    private var onItemClickCallback: OnItemClickCallback? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemBookBigBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val library = listLibrary[position]
        holder.binding.apply {
            tvBookTitle.text = library.book.title
            Glide.with(holder.itemView.context)
                .load(library.book.urlImage)
                .sizeMultiplier(0.5f)
                .placeholder(R.drawable.sample_book)
                .optionalFitCenter()
                .transform(RoundedCorners(30))
                .into(imgBook)
            root.setOnClickListener {
                onItemClickCallback?.onItemClicked(library)
            }
        }
    }

    override fun getItemCount(): Int = listLibrary.size

    fun setLibraryBooks(books: List<LibraryItem>) {
        listLibrary.clear()
        listLibrary.addAll(books)
        notifyDataSetChanged()
    }

    class ViewHolder(val binding: ItemBookBigBinding) : RecyclerView.ViewHolder(binding.root)

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: LibraryItem)
    }
}
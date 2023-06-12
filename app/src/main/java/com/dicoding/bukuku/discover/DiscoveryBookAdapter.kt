package com.dicoding.bukuku.discover

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.dicoding.bukuku.R
import com.dicoding.bukuku.databinding.ItemBookBigBinding
import com.dicoding.bukuku.response.BooksItem

class DiscoveryBookAdapter : PagingDataAdapter<BooksItem, DiscoveryBookAdapter.ViewHolder>(
    DIFF_CALLBACK
) {

    private var onItemClickCallback: OnItemClickCallback? = null

    inner class ViewHolder(val binding: ItemBookBigBinding) : RecyclerView.ViewHolder(binding.root)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemBookBigBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val book = getItem(position)
        with(holder.binding) {
            tvBookTitle.text = book?.title
            Glide.with(holder.itemView.context)
                .load(book?.urlImage)
                .sizeMultiplier(0.5f)
                .placeholder(R.drawable.sample_book)
                .optionalFitCenter()
                .transform(RoundedCorners(30))
                .into(imgBook)
            root.setOnClickListener {
                if (book != null) {
                    onItemClickCallback?.onItemClicked(book)
                }
            }
        }
    }


    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: BooksItem)
    }

    private companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<BooksItem>() {
            override fun areItemsTheSame(oldItem: BooksItem, newItem: BooksItem): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: BooksItem, newItem: BooksItem): Boolean {
                return oldItem == newItem
            }
        }
    }
}

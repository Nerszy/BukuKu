package com.dicoding.bukuku.search


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.bukuku.databinding.ListItemSearchBinding
import com.dicoding.bukuku.response.BooksItem

class SearchAdapter : RecyclerView.Adapter<SearchAdapter.ViewHolder>() {

    private val listSearch = ArrayList<BooksItem>()
    private var onItemClickCallback: OnItemClickCallback? = null

    inner class ViewHolder(val binding: ListItemSearchBinding) :
        RecyclerView.ViewHolder(binding.root) {
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ListItemSearchBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = listSearch.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val book = listSearch[position]
        holder.binding.apply {
            tvBookTitle.text = book.title
            root.setOnClickListener {
                onItemClickCallback?.onItemClicked(book)
            }
        }
    }

    fun submitList(newList: List<BooksItem>) {
        val diffCallback = SearchDiffCallback(listSearch, newList)
        val diffResult = DiffUtil.calculateDiff(diffCallback)

        listSearch.clear()
        listSearch.addAll(newList)

        diffResult.dispatchUpdatesTo(this)
    }

    fun setOnItemClickCallbackSearch(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: BooksItem)
    }

    class SearchDiffCallback(
        private val oldList: List<BooksItem>,
        private val newList: List<BooksItem>
    ) : DiffUtil.Callback() {

        override fun getOldListSize(): Int = oldList.size

        override fun getNewListSize(): Int = newList.size

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            val oldItem = oldList[oldItemPosition]
            val newItem = newList[newItemPosition]
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            val oldItem = oldList[oldItemPosition]
            val newItem = newList[newItemPosition]
            return oldItem == newItem
        }
    }

}



package com.dicoding.bukuku

import android.view.LayoutInflater
import kotlin.math.min
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.dicoding.bukuku.databinding.ItemBookBigBinding
import com.dicoding.bukuku.response.BooksItem

class RecommendAdapter : RecyclerView.Adapter<RecommendAdapter.ViewHolder>() {

    private val listRecommend = ArrayList<BooksItem>()
    private var isLimitedMode: Boolean = false
    private var onItemClickCallback: OnItemClickCallback? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemBookBigBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val recommend = listRecommend[position]
        holder.binding.apply {
            tvBookTitle.text = recommend.title
            Glide.with(holder.itemView.context)
                .load(recommend.urlImage)
                .sizeMultiplier(0.5f)
                .placeholder(R.drawable.sample_book)
                .optionalFitCenter()
                .transform(RoundedCorners(30))
                .into(imgBook)
            root.setOnClickListener {
                onItemClickCallback?.onItemClicked(recommend)
            }
        }
    }

    override fun getItemCount(): Int {
        return if (isLimitedMode) {
            min(listRecommend.size, 4) // Batasi jumlah item menjadi 4 jika dalam mode terbatas
        } else {
            listRecommend.size // Tampilkan semua item jika tidak dalam mode terbatas
        }
    }

    fun setLimitedMode(isLimited: Boolean) {
        isLimitedMode = isLimited
        notifyDataSetChanged()
    }


    fun setRecommendBooks(books: List<BooksItem>) {
        listRecommend.clear()
        listRecommend.addAll(books)
        notifyDataSetChanged()
    }

    class ViewHolder(val binding: ItemBookBigBinding) : RecyclerView.ViewHolder(binding.root)

    fun setOnItemClickCallbackRecommend(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: BooksItem)
    }
}

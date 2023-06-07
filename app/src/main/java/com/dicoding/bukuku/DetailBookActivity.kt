package com.dicoding.bukuku

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.Html
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.dicoding.bukuku.databinding.ActivityDetailBookBinding

class DetailBookActivity : AppCompatActivity() {

    private val binding: ActivityDetailBookBinding by lazy {
        ActivityDetailBookBinding.inflate(layoutInflater)
    }

    private val viewModel: DetailBookViewModel by lazy {
        DetailBookViewModel()
    }

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        // set home as up
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val id = intent.getIntExtra(EXTRA_ID, 0)

        viewModel.getBookById(id)
        viewModel.book.observe(this) { book ->
            book?.let { listBook ->
                @Suppress("DEPRECATION")
                binding.apply {
                    tvBookTitle.text = listBook.title
                    tvSynopsis.text = Html.fromHtml(listBook.synopsis)
                    tvSynopsis.maxLines = Int.MAX_VALUE
                    tvPrice.text = listBook.idr
                    tvBuy.setOnClickListener {
                        val browserIntent =
                            Intent(Intent.ACTION_VIEW, Uri.parse(listBook.urlPlaybook))
                        startActivity(browserIntent)
                    }
                    tvRating.text = "Average Rating: ${listBook.avgRating}"
                    tvIsbn.text = "ISBN: ${listBook.isbn}"
                    tvAuthor.text = "Author: ${listBook.author}"
                    tvTags.text =
                        "Included Tags: ${listBook.tags1}, ${listBook.tags2}, ${listBook.tags3}"

                }
                Glide.with(this)
                    .load(listBook.urlImage)
                    .skipMemoryCache(true)
                    .placeholder(R.drawable.sample_book)
                    .centerCrop()
                    .into(binding.imgBook)
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    companion object {
        const val EXTRA_ID = "extra_id"
    }
}

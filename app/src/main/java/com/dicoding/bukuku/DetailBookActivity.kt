package com.dicoding.bukuku

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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        // set home as up
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val url = intent.getStringExtra(EXTRA_URL)
        val title = intent.getStringExtra(EXTRA_TITLE)
        val synopsis = intent.getStringExtra(EXTRA_SYNOPSIS)
        val price = intent.getStringExtra(EXTRA_PRICE)
        val playbook = intent.getStringExtra(EXTRA_PLAYBOOK)

        @Suppress("DEPRECATION")
        binding.apply {
            tvBookTitle.text = title
            tvSynopsis.text = Html.fromHtml(synopsis)
            tvPrice.text = price
            tvBuy.setOnClickListener {
                val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(playbook))
                startActivity(browserIntent)
            }

        }
        Glide.with(this)
            .load(url)
            .skipMemoryCache(true)
            .centerCrop()
            .into(binding.imgBook)
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
        const val EXTRA_URL = "extra_url"
        const val EXTRA_TITLE = "extra_title"
        const val EXTRA_SYNOPSIS = "extra_synopsis"
        const val EXTRA_PRICE = "extra_price"
        const val EXTRA_PLAYBOOK = "extra_playbook"
    }
}

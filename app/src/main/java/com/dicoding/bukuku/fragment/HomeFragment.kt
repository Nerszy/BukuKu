package com.dicoding.bukuku.fragment

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.bukuku.DetailBookActivity
import com.dicoding.bukuku.RecommendAdapter
import com.dicoding.bukuku.RecommendRequest
import com.dicoding.bukuku.RecommendViewModel
import com.dicoding.bukuku.databinding.FragmentHomeBinding
import com.dicoding.bukuku.response.BooksItem
import com.dicoding.bukuku.search.SearchAdapter
import com.dicoding.bukuku.search.SearchViewModel

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var adapter: SearchAdapter
    private lateinit var recommendAdapter: RecommendAdapter
    private lateinit var recommendViewModel: RecommendViewModel
    private lateinit var searchViewModel: SearchViewModel
    private val searchDelayMillis = 1000L

    private val searchHandler = Handler(Looper.getMainLooper())
    private var searchRunnable: Runnable? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = SearchAdapter()
        binding.lvSearch.adapter = adapter
        binding.lvSearch.layoutManager = LinearLayoutManager(requireContext())

        recommendAdapter = RecommendAdapter()
        recommendAdapter.setLimitedMode(true)
        binding.rvRecommendation.adapter = recommendAdapter
        binding.rvRecommendation.layoutManager = GridLayoutManager(requireContext(), 2)

        searchViewModel = ViewModelProvider(this)[SearchViewModel::class.java]
        recommendViewModel = ViewModelProvider(this)[RecommendViewModel::class.java]

        recommendViewModel.listRecommendBook.observe(viewLifecycleOwner) { recommendList ->
            recommendAdapter.setRecommendBooks(recommendList)
        }

        binding.svSearch.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                searchRunnable?.let { searchHandler.removeCallbacks(it) }
                performSearch(query)
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                searchRunnable?.let { searchHandler.removeCallbacks(it) }
                searchRunnable = Runnable { performSearch(newText) }
                searchHandler.postDelayed(searchRunnable!!, searchDelayMillis)
                return true
            }
        })

        binding.progressBook.setOnClickListener {
            clearSearch()
        }

        adapter.setOnItemClickCallbackSearch(object : SearchAdapter.OnItemClickCallback {
            override fun onItemClicked(data: BooksItem) {
                val intent = Intent(requireContext(), DetailBookActivity::class.java).apply {
                    putExtra(DetailBookActivity.EXTRA_ID, data.booksId)
                }
                startActivity(intent)
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        searchRunnable?.let { searchHandler.removeCallbacks(it) }
    }

    private fun performSearch(query: String) {
        if (query.isBlank()) {
            clearSearch()
        } else {
            searchViewModel.getSearchBook(query)
        }
    }

    private fun clearSearch() {
        adapter.submitList(emptyList())
        binding.lvSearch.visibility = View.GONE
        binding.svSearch.setQuery("", false)
        binding.svSearch.clearFocus()
        binding.lvSearch.clearFocus()
    }

    private fun showLoading(isLoading: Boolean) {
        binding.pbSearch.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    override fun onResume() {
        super.onResume()

        searchViewModel.listBook.observe(viewLifecycleOwner) { bookList ->
            adapter.submitList(bookList)
            binding.lvSearch.visibility = if (bookList.isNotEmpty()) View.VISIBLE else View.GONE
        }

        searchViewModel.errorState.observe(viewLifecycleOwner) { errorCode ->
            val errorMessage = when (errorCode) {
                404 -> "Book not found"
                500 -> "Internal server error"
                else -> return@observe
            }
            adapter.submitList(emptyList())
            binding.lvSearch.visibility = View.GONE
            Toast.makeText(requireContext(), errorMessage, Toast.LENGTH_SHORT).show()
        }

        searchViewModel.loadingState.observe(viewLifecycleOwner) { isLoading ->
            showLoading(isLoading)
        }

        val selectedBooks = arguments?.getIntegerArrayList(EXTRA_SELECTED_BOOKS)
        val alternativeSelected = arrayListOf(348, 343, 320)
        if (selectedBooks != null && selectedBooks.isNotEmpty()) {
            val recommendRequest = RecommendRequest(selectedBooks)
            recommendViewModel.getRecommendBook(viewLifecycleOwner,recommendRequest)
        } else {
            val recommendRequest = RecommendRequest(alternativeSelected)
            recommendViewModel.getRecommendBook(viewLifecycleOwner,recommendRequest)
        }
    }

    companion object {
        const val EXTRA_SELECTED_BOOKS = "extra_selected_books"
    }
}

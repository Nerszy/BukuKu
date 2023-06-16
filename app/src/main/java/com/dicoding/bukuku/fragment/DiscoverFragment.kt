package com.dicoding.bukuku.fragment

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.bukuku.tools.ApiConfig
import com.dicoding.bukuku.tools.BookRepository
import com.dicoding.bukuku.DetailBookActivity
import com.dicoding.bukuku.RecommendAdapter
import com.dicoding.bukuku.RecommendRequest
import com.dicoding.bukuku.RecommendViewModel
import com.dicoding.bukuku.tools.LoadingStateAdapter
import com.dicoding.bukuku.databinding.FragmentDiscoverBinding
import com.dicoding.bukuku.discover.DiscoverViewModel
import com.dicoding.bukuku.discover.DiscoverViewModelFactory
import com.dicoding.bukuku.discover.DiscoveryBookAdapter
import com.dicoding.bukuku.response.BooksItem
import com.dicoding.bukuku.search.SearchAdapter
import com.dicoding.bukuku.search.SearchViewModel
import kotlinx.coroutines.launch

class DiscoverFragment : Fragment() {

    private var _binding: FragmentDiscoverBinding? = null
    private val binding get() = _binding!!
    private lateinit var searchAdapter: SearchAdapter
    private lateinit var searchViewModel: SearchViewModel
    private lateinit var recommendAdapter: RecommendAdapter
    private lateinit var recommendViewModel: RecommendViewModel
    private val searchDelayMillis = 1000L

    private val searchHandler = Handler(Looper.getMainLooper())
    private var searchRunnable: Runnable? = null

    private val adapter: DiscoveryBookAdapter by lazy {
        DiscoveryBookAdapter()
    }

    private lateinit var mDiscover: DiscoverViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDiscoverBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        search()
        rekomen()

        binding.rvBook.layoutManager = GridLayoutManager(requireContext(), 2)
        binding.rvBook.setHasFixedSize(true)
        binding.rvBook.adapter = adapter.withLoadStateFooter(
            footer = LoadingStateAdapter { adapter.retry() }
        )

        val bookRepository = BookRepository(ApiConfig.getApiServices())

        val viewModelFactory = DiscoverViewModelFactory(bookRepository)
        mDiscover = ViewModelProvider(this, viewModelFactory)[DiscoverViewModel::class.java]

        showLoadingDiscovery(true)
        viewLifecycleOwner.lifecycleScope.launch {
            mDiscover.listBook.observe(viewLifecycleOwner) { bookList ->
                adapter.submitData(viewLifecycleOwner.lifecycle, bookList)
            }
        }

        adapter.addLoadStateListener { loadStates ->
            val refreshState = loadStates.refresh
            if (refreshState is LoadState.NotLoading || refreshState is LoadState.Error) {
                showLoadingDiscovery(false)
            } else {
                showLoadingDiscovery(true)
            }
        }

        adapter.setOnItemClickCallback(object : DiscoveryBookAdapter.OnItemClickCallback {
            override fun onItemClicked(data: BooksItem) {
                val intent = Intent(requireContext(), DetailBookActivity::class.java).apply {
                    putExtra(DetailBookActivity.EXTRA_ID, data.booksId)
                }
                startActivity(intent)
            }
        })
    }

    private fun rekomen(){
        recommendAdapter = RecommendAdapter()
        recommendAdapter.setLimitedMode(false)
        binding.rvRecommend.adapter = recommendAdapter
        binding.rvRecommend.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

        recommendViewModel = ViewModelProvider(this)[RecommendViewModel::class.java]

        recommendViewModel.listRecommendBook.observe(viewLifecycleOwner) { recommendList ->
            recommendAdapter.setRecommendBooks(recommendList)
        }

        val selectedBooks = arguments?.getIntegerArrayList(HomeFragment.EXTRA_SELECTED_BOOKS)
        val alternativeSelected = arrayListOf(1, 2, 3)
        if (selectedBooks != null && selectedBooks.isNotEmpty()) {
            val recommendRequest = RecommendRequest(selectedBooks)
            recommendViewModel.getRecommendBook(viewLifecycleOwner,recommendRequest)
        } else {
            val recommendRequest = RecommendRequest(alternativeSelected)
            recommendViewModel.getRecommendBook(viewLifecycleOwner,recommendRequest)
        }

    }

    private fun search() {
        searchAdapter = SearchAdapter()
        binding.lvSearch.adapter = searchAdapter
        binding.lvSearch.layoutManager = LinearLayoutManager(requireContext())

        searchViewModel = ViewModelProvider(this)[SearchViewModel::class.java]

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

        binding.discoverConstraint.setOnClickListener {
            clearSearch()
        }

        searchAdapter.setOnItemClickCallbackSearch(object : SearchAdapter.OnItemClickCallback {
            override fun onItemClicked(data: BooksItem) {
                val intent = Intent(requireContext(), DetailBookActivity::class.java).apply {
                    putExtra(DetailBookActivity.EXTRA_ID, data.booksId)
                }
                startActivity(intent)
            }
        })
    }

    private fun performSearch(query: String) {
        if (query.isBlank()) {
            clearSearch()
        } else {
            searchViewModel.getSearchBook(query)
        }
    }

    private fun clearSearch() {
        searchAdapter.submitList(emptyList())
        binding.lvSearch.visibility = View.GONE
        binding.svSearch.setQuery("", false)
        binding.svSearch.clearFocus()
        binding.lvSearch.clearFocus()
    }


    private fun showLoadingDiscovery(isLoading: Boolean) {
        binding.pbRecyclerview.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun showLoadingSearch(isLoading: Boolean) {
        binding.pbSearch.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    override fun onResume() {
        super.onResume()

        searchViewModel.listBook.observe(viewLifecycleOwner) { bookList ->
            searchAdapter.submitList(bookList)
            binding.lvSearch.visibility = if (bookList.isNotEmpty()) View.VISIBLE else View.GONE
        }

        searchViewModel.errorState.observe(viewLifecycleOwner) { errorCode ->
            val errorMessage = when (errorCode) {
                404 -> "Book not found"
                500 -> "Internal server error"
                else -> return@observe
            }
            searchAdapter.submitList(emptyList())
            binding.lvSearch.visibility = View.GONE
            Toast.makeText(requireContext(), errorMessage, Toast.LENGTH_SHORT).show()
        }

        searchViewModel.loadingState.observe(viewLifecycleOwner) { isLoading ->
            showLoadingSearch(isLoading)
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

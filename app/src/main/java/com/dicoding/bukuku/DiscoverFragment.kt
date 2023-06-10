package com.dicoding.bukuku

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.dicoding.bukuku.databinding.FragmentDiscoverBinding
import com.dicoding.bukuku.response.BooksItem
import kotlinx.coroutines.launch

class DiscoverFragment : Fragment() {

    private var _binding: FragmentDiscoverBinding? = null
    private val binding get() = _binding!!

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

        binding.rvBook.layoutManager = GridLayoutManager(requireContext(), 2)
        binding.rvBook.setHasFixedSize(true)
        binding.rvBook.adapter = adapter.withLoadStateFooter(
            footer = LoadingStateAdapter { adapter.retry() }
        )

        val bookRepository = BookRepository(ApiConfig.getApiServices())

        val viewModelFactory = DiscoverViewModelFactory(bookRepository)
        mDiscover = ViewModelProvider(this, viewModelFactory)[DiscoverViewModel::class.java]

        mDiscover.listBook.observe(viewLifecycleOwner) { bookList ->
            if (bookList != null) {
                adapter.submitData(viewLifecycleOwner.lifecycle, bookList)
            }
            Log.d("DiscoverFragment", "onViewCreated: $bookList")
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

    private fun showLoading(isLoading: Boolean) {
        binding.pbRecyclerview.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

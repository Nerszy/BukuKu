package com.dicoding.bukuku

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.dicoding.bukuku.databinding.FragmentDiscoverBinding
import com.dicoding.bukuku.response.BooksItem

class DiscoverFragment : Fragment() {

    private var _binding: FragmentDiscoverBinding? = null
    private val binding get() = _binding!!

    private val adapter: SearchBookAdapter by lazy {
        SearchBookAdapter()
    }

    private lateinit var mSearch: SearchViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDiscoverBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rvBook.layoutManager = GridLayoutManager(requireContext(), 2)
        binding.rvBook.setHasFixedSize(true)
        binding.rvBook.adapter = adapter

        mSearch = ViewModelProvider(this)[SearchViewModel::class.java]

        mSearch.setBook()
        mSearch.listBook.observe(viewLifecycleOwner) { bookList ->
            if (bookList != null) {
                adapter.setBooks(bookList)
            }
        }

        adapter.setOnItemClickCallback(object : SearchBookAdapter.OnItemClickCallback {
            override fun onItemClicked(data: BooksItem) {
                Intent(requireContext(), DetailBookActivity::class.java).also {
                    it.putExtra(DetailBookActivity.EXTRA_URL, data.urlImage)
                    it.putExtra(DetailBookActivity.EXTRA_TITLE, data.title)
                    it.putExtra(DetailBookActivity.EXTRA_SYNOPSIS, data.synopsis)
                    it.putExtra(DetailBookActivity.EXTRA_PRICE, data.idr)
                    it.putExtra(DetailBookActivity.EXTRA_PLAYBOOK, data.urlPlaybook)
                    startActivity(it)
                }
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}


package com.dicoding.bukuku.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.dicoding.bukuku.LibraryAdapter
import com.dicoding.bukuku.LibrrayViewModel
import com.dicoding.bukuku.R
import com.dicoding.bukuku.UserPreference
import com.dicoding.bukuku.databinding.FragmentLibraryBinding
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "user")


class LibraryFragment : Fragment() {

    private lateinit var binding: FragmentLibraryBinding
    private val adapter: LibraryAdapter by lazy { LibraryAdapter() }
    private val libraryViewModel: LibrrayViewModel by viewModels()
    private val userPreference: UserPreference by lazy {
        UserPreference.getInstance(requireContext().dataStore)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLibraryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        library()

        var clicked = false
        binding.imgStar.setOnClickListener {
            //set if clicked change to filled star
            if (clicked) {
                clicked = false
                binding.imgStar.setImageResource(R.drawable.star)
            } else {
                clicked = true
                binding.imgStar.setImageResource(R.drawable.filled_star)
            }
        }
    }

    private fun library(){
        binding.rvLibrary.adapter = adapter
        binding.rvLibrary.layoutManager = GridLayoutManager(requireContext(), 2)
        lifecycleScope.launch {
            val user = userPreference.getUser().first()
            libraryViewModel.getLibrary(user.username)
        }
        libraryViewModel.library.observe(viewLifecycleOwner) { libraryList ->
            adapter.setLibraryBooks(libraryList)
        }

    }

}
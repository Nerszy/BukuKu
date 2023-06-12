package com.dicoding.bukuku

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.dicoding.bukuku.databinding.ActivityMainBinding
import com.dicoding.bukuku.fragment.DiscoverFragment
import com.dicoding.bukuku.fragment.HomeFragment
import com.dicoding.bukuku.fragment.LibraryFragment
import com.dicoding.bukuku.fragment.ProfileFragment

class MainActivity : AppCompatActivity() {

    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private lateinit var currentFragment: Fragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        supportActionBar?.hide()

        binding.bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.iconHome -> {
                    replaceFragment(HomeFragment())
                    true
                }
                R.id.iconLibrary -> {
                    replaceFragment(LibraryFragment())
                    true
                }
                R.id.iconDiscover -> {
                    replaceFragment(DiscoverFragment())
                    true
                }
                R.id.iconProfile -> {
                    replaceFragment(ProfileFragment())
                    true
                }
                else -> false
            }
        }

        // Set HomeFragment as the default fragment
        currentFragment = HomeFragment()
        supportFragmentManager.beginTransaction()
            .replace(R.id.frame_container, currentFragment)
            .commit()
    }

    private fun replaceFragment(fragment: Fragment) {
        val enterAnim: Int
        val exitAnim: Int
        val popEnterAnim: Int
        val popExitAnim: Int

        val currentIndex = getFragmentIndex(currentFragment)
        val targetIndex = getFragmentIndex(fragment)

        enterAnim = if (targetIndex < currentIndex) {
            R.anim.slide_in_from_left
        } else {
            R.anim.slide_in_from_right
        }

        exitAnim = if (targetIndex < currentIndex) {
            R.anim.slide_out_to_right
        } else {
            R.anim.slide_out_to_left
        }

        popEnterAnim = if (targetIndex < currentIndex) {
            R.anim.slide_in_from_right
        } else {
            R.anim.slide_in_from_left
        }

        popExitAnim = if (targetIndex < currentIndex) {
            R.anim.slide_out_to_left
        } else {
            R.anim.slide_out_to_right
        }

        supportFragmentManager.beginTransaction()
            .setCustomAnimations(enterAnim, exitAnim, popEnterAnim, popExitAnim)
            .replace(R.id.frame_container, fragment)
            .commit()

        currentFragment = fragment
    }

    private fun getFragmentIndex(fragment: Fragment): Int {
        return when (fragment) {
            is HomeFragment -> 0
            is LibraryFragment -> 1
            is DiscoverFragment -> 2
            is ProfileFragment -> 3
            else -> -1
        }
    }


    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        val homeFragment = HomeFragment()

        if (currentFragment !is HomeFragment) {
            replaceFragment(homeFragment)
            binding.bottomNavigation.selectedItemId = R.id.iconHome
        } else {
            super.onBackPressed()
        }
    }
}
package com.dicoding.bukuku.login

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.dicoding.bukuku.*
import com.dicoding.bukuku.initialgenre.InitialGenreActivity
import com.dicoding.bukuku.databinding.ActivityLoginBinding

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "user")

@Suppress("DEPRECATION")
class LoginActivity : AppCompatActivity() {
    private val binding: ActivityLoginBinding by lazy {
        ActivityLoginBinding.inflate(layoutInflater)
    }

    private val authViewModel: AuthorizationViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
        supportActionBar?.hide()

        login()

        binding.tvNeedRegister.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
            finish()
        }
    }

    private fun login() {
        binding.etUsername.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                binding.etUsername.error = null
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // Do nothing
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // Do nothing
            }
        })

        binding.etPassword.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                binding.etPassword.error = null
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // Do nothing
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // Do nothing
            }
        })

        binding.btnLogin.setOnClickListener {
            val username = binding.etUsername.text.toString()
            val password = binding.etPassword.text.toString()

            val usernameError = binding.etUsername.error
            val passwordError = binding.etPassword.error

            if (username.isEmpty()) {
                Toast.makeText(this, "Username must be filled", Toast.LENGTH_SHORT).show()
                binding.etUsername.requestFocus()
                return@setOnClickListener
            }

            if (password.isEmpty()) {
                Toast.makeText(this, "Password must be filled", Toast.LENGTH_SHORT).show()
                binding.etPassword.requestFocus()
                return@setOnClickListener
            }

            if (!isPasswordValid(password)) {
                Toast.makeText(this, "Password must be at least 8 characters", Toast.LENGTH_SHORT).show()
                binding.etPassword.requestFocus()
                return@setOnClickListener
            }

            if (usernameError != null || passwordError != null) {
                binding.btnLogin.requestFocus()
                return@setOnClickListener
            }

            authViewModel.setLogin(username, password, userPreference = UserPreference.getInstance(dataStore))
        }

        authViewModel.error.observe(this) { error ->
            if (!error.isNullOrEmpty()) {
                Toast.makeText(this, error, Toast.LENGTH_SHORT).show()
            }
        }

        authViewModel.responseAuth.observe(this) { response ->
            response?.let {
                if (!it.err) {
                    // Login successful
                    Toast.makeText(this, "Login successful", Toast.LENGTH_SHORT).show()
                    // Add your desired logic here
                    startActivity(Intent(this, InitialGenreActivity::class.java))
                    finish()
                } else {
                    Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun isPasswordValid(password: String): Boolean {
        return password.length >= 8
    }
}

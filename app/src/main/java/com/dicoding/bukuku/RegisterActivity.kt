package com.dicoding.bukuku

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Patterns
import android.view.View
import android.view.View.OnTouchListener
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.dicoding.bukuku.databinding.ActivityRegisterBinding
import com.dicoding.bukuku.login.LoginActivity

@Suppress("DEPRECATION")
class RegisterActivity : AppCompatActivity() {
    private val binding: ActivityRegisterBinding by lazy {
        ActivityRegisterBinding.inflate(layoutInflater)
    }

    private val authViewModel: AuthorizationViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
        supportActionBar?.hide()

        signUp()

        binding.tvLogin.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }

    private fun signUp() {
        binding.edtEmail.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                if (validateEmail(binding.edtEmail.text.toString())) {
                    binding.edtEmail.error = null
                } else {
                    binding.edtEmail.error = "Email not valid"
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                binding.edtEmail.error = null
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // Do nothing
            }
        })

        binding.edtUsername.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                binding.edtUsername.error = null
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // Do nothing
            }
        })

        binding.edtPassword.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                if (validatePassword(binding.edtPassword.text.toString())) {
                    binding.edtPassword.error = null
                    binding.passwordInputLayout.isErrorEnabled = false
                } else {
                    binding.edtPassword.error = "Password minimum 8 characters"
                    binding.passwordInputLayout.isErrorEnabled = true
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                binding.edtPassword.error = null
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // Do nothing
            }
        })

        binding.btnSignUp.setOnClickListener {
            val email = binding.edtEmail.text.toString()
            val username = binding.edtUsername.text.toString()
            val password = binding.edtPassword.text.toString()
            val emailError = binding.edtEmail.error
            val passwordError = binding.edtPassword.error

            if (email.isEmpty()) {
                binding.edtEmail.error = "Email must be filled"
                binding.edtEmail.requestFocus()
                return@setOnClickListener
            }
            if (username.isEmpty()) {
                binding.edtUsername.error = "Username must be filled"
                binding.edtUsername.requestFocus()
                return@setOnClickListener
            }
            if (password.isEmpty()) {
                binding.edtPassword.error = "Password must be filled"
                binding.edtPassword.requestFocus()
                return@setOnClickListener
            }
            if (emailError != null || passwordError != null) {
                binding.btnSignUp.requestFocus()
                return@setOnClickListener
            }

            authViewModel.setRegister(email, username, password)
        }

        authViewModel.error.observe(this) { error ->
            if (!error.isNullOrEmpty()) {
                Toast.makeText(this, error, Toast.LENGTH_SHORT).show()
            }
        }

        authViewModel.responseAuth.observe(this) { response ->
            response?.let {
                if (!it.err) {
                    val intent = Intent(this, LoginActivity::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun validateEmail(email: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    private fun validatePassword(password: String): Boolean {
        return password.length >= 8
    }
}

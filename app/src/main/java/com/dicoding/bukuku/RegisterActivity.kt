package com.dicoding.bukuku

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.dicoding.bukuku.databinding.ActivityRegisterBinding
import com.dicoding.bukuku.login.LoginActivity

@Suppress("DEPRECATION")
class RegisterActivity : AppCompatActivity() {
    private val binding: ActivityRegisterBinding by lazy {
        ActivityRegisterBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
        supportActionBar?.hide()

        binding.btnSignUp.setOnClickListener {
//            val email = binding.edtEmail.text.toString()
//            val username = binding.edtUsername.text.toString()
//            val password = binding.edtPassword.text.toString()
//
//            val isEmailValid = isValidEmail(email)
//            val isPasswordValid = isPasswordValid(password)
//
//            if (isEmailValid && isPasswordValid) {
//                // Enkripsi password menggunakan BCrypt
//                val hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt())
//
//                // Simpan hashedPassword ke database
//                //Munculkan toast dai hashpassword
//                Toast.makeText(this, hashedPassword, Toast.LENGTH_SHORT).show()
//
////                Toast.makeText(this, "Registrasi berhasil!", Toast.LENGTH_SHORT).show()
//
//                // Redirect ke halaman login atau tindakan selanjutnya
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
//
//        binding.edtEmail.addTextChangedListener(object : TextWatcher {
//            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
//                // Tidak diperlukan dalam implementasi ini
//            }
//
//            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
//                val email = s.toString()
//                val isEmailValid = isValidEmail(email)
//                if (!isEmailValid) {
//                    binding.emailInputLayout.error = "Email tidak valid"
//                } else {
//                    binding.emailInputLayout.error = null
//                }
//            }
//
//            override fun afterTextChanged(s: Editable?) {
//                // Tidak diperlukan dalam implementasi ini
//            }
//        })
//
//        binding.edtPassword.addTextChangedListener(object : TextWatcher {
//            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
//                // Tidak diperlukan dalam implementasi ini
//            }
//
//            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
//                val password = s.toString()
//                val isPasswordValid = isPasswordValid(password)
//                if (!isPasswordValid) {
//                    binding.passwordInputLayout.error = "Password minimal 8 karakter"
//                } else {
//                    binding.passwordInputLayout.error = null
//                }
//            }
//
//            override fun afterTextChanged(s: Editable?) {
//                // Tidak diperlukan dalam implementasi ini
//            }
//        })
//    }
//
//    private fun isValidEmail(email: String): Boolean {
//        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
//    }
//
//    private fun isPasswordValid(password: String): Boolean {
//        return password.length >= 8
//    }
}

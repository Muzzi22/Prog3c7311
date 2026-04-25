package com.example.flowfund

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class RegisterActivity : AppCompatActivity() {

    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        sharedPreferences = getSharedPreferences("FlowFundUsers", MODE_PRIVATE)

        val etFullName = findViewById<EditText>(R.id.etFullName)
        val etUsername = findViewById<EditText>(R.id.etUsername)
        val etEmail = findViewById<EditText>(R.id.etRegEmail)
        val etPassword = findViewById<EditText>(R.id.etRegPassword)
        val etConfirmPassword = findViewById<EditText>(R.id.etConfirmPassword)
        val cbTerms = findViewById<CheckBox>(R.id.cbTerms)
        val btnRegister = findViewById<Button>(R.id.btnRegister)
        val tvSignIn = findViewById<TextView>(R.id.tvSignIn)

        btnRegister.setOnClickListener {
            val fullName = etFullName.text.toString().trim()
            val username = etUsername.text.toString().trim()
            val email = etEmail.text.toString().trim()
            val password = etPassword.text.toString().trim()
            val confirmPassword = etConfirmPassword.text.toString().trim()

            when {
                fullName.isEmpty() || username.isEmpty() || email.isEmpty()
                        || password.isEmpty() || confirmPassword.isEmpty() -> {
                    Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
                }
                !email.contains("@") -> {
                    Toast.makeText(this, "Please enter a valid email address", Toast.LENGTH_SHORT).show()
                }
                password != confirmPassword -> {
                    Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show()
                }
                password.length < 6 -> {
                    Toast.makeText(this, "Password must be at least 6 characters", Toast.LENGTH_SHORT).show()
                }
                !cbTerms.isChecked -> {
                    Toast.makeText(this, "Please agree to the Terms & Privacy Policy", Toast.LENGTH_SHORT).show()
                }
                sharedPreferences.contains(email) -> {
                    Toast.makeText(this, "An account with this email already exists.", Toast.LENGTH_LONG).show()
                }
                else -> {
                    // Save the user's email and password
                    sharedPreferences.edit()
                        .putString(email, password)
                        .putString("${email}_name", fullName)
                        .putString("${email}_username", username)
                        .apply()

                    Toast.makeText(this, "Account created! Welcome, $username 🎉", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, CategoryActivity::class.java)
                    startActivity(intent)
                    finish()
                }
            }
        }

        tvSignIn.setOnClickListener {
            finish()
        }
    }
}
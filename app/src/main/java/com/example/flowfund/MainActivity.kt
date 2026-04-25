package com.example.flowfund

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // This is where we store registered users
        sharedPreferences = getSharedPreferences("FlowFundUsers", MODE_PRIVATE)

        val etEmail = findViewById<EditText>(R.id.etEmail)
        val etPassword = findViewById<EditText>(R.id.etPassword)
        val btnSignIn = findViewById<Button>(R.id.btnSignIn)
        val btnCreateAccount = findViewById<Button>(R.id.btnCreateAccount)
        val tvForgotPassword = findViewById<TextView>(R.id.tvForgotPassword)

        btnSignIn.setOnClickListener {
            val email = etEmail.text.toString().trim()
            val password = etPassword.text.toString().trim()

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Check if this email has been registered
            val savedPassword = sharedPreferences.getString(email, null)

            when {
                savedPassword == null -> {
                    // Email not found — not registered
                    Toast.makeText(
                        this,
                        "No account found. Please register first.",
                        Toast.LENGTH_LONG
                    ).show()
                }
                savedPassword != password -> {
                    // Wrong password
                    Toast.makeText(
                        this,
                        "Incorrect password. Please try again.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                else -> {
                    // Correct email and password — go to 2FA
                    Toast.makeText(this, "Welcome back! 👋", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, TwoFactorActivity::class.java)
                    intent.putExtra("email", email)
                    startActivity(intent)
                }
            }
        }

        btnCreateAccount.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }

        tvForgotPassword.setOnClickListener {
            Toast.makeText(this, "Please contact support to reset your password.", Toast.LENGTH_LONG).show()
        }
    }
}
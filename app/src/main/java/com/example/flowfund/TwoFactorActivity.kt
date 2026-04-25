package com.example.flowfund

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import kotlin.random.Random

class TwoFactorActivity : AppCompatActivity() {

    private var generatedCode: String = ""
    private var userEmail: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_two_factor)

        userEmail = intent.getStringExtra("email") ?: ""

        val tvEmailSent = findViewById<TextView>(R.id.tvEmailSent)
        val btnVerify = findViewById<Button>(R.id.btnVerify)
        val tvResend = findViewById<TextView>(R.id.tvResend)
        val tvBackToSignIn = findViewById<TextView>(R.id.tvBackToSignIn)

        val otpBoxes = listOf(
            findViewById<EditText>(R.id.otp1),
            findViewById<EditText>(R.id.otp2),
            findViewById<EditText>(R.id.otp3),
            findViewById<EditText>(R.id.otp4),
            findViewById<EditText>(R.id.otp5),
            findViewById<EditText>(R.id.otp6)
        )

        tvEmailSent.text = "Code sent to $userEmail"

        // Generate and show the code (for prototype testing)
        generateAndShowCode()

        // Auto-jump to next box when a digit is entered
        otpBoxes.forEachIndexed { index, editText ->
            editText.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(s: Editable?) {
                    if (s?.length == 1 && index < otpBoxes.size - 1) {
                        otpBoxes[index + 1].requestFocus()
                    }
                }
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            })
        }

        btnVerify.setOnClickListener {
            val enteredCode = otpBoxes.joinToString("") { it.text.toString() }

            if (enteredCode.length < 6) {
                Toast.makeText(this, "Please enter the full 6-digit code", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (enteredCode == generatedCode) {
                Toast.makeText(this, "Verified! Welcome 🎉", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, HomeActivity::class.java)
                startActivity(intent)
                finish()
            } else {
                Toast.makeText(this, "Incorrect code. Please try again.", Toast.LENGTH_SHORT).show()
            }
        }

        tvResend.setOnClickListener {
            generateAndShowCode()
        }

        tvBackToSignIn.setOnClickListener {
            finish()
        }
    }

    private fun generateAndShowCode() {
        generatedCode = Random.nextInt(100000, 999999).toString()
        // Show the code on screen for prototype demonstration
        Toast.makeText(this, "Your OTP code is: $generatedCode", Toast.LENGTH_LONG).show()
    }
}
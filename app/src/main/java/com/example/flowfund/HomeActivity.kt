package com.example.flowfund

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.text.SimpleDateFormat
import java.util.*

class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        // Set current month and year dynamically
        val tvDate = findViewById<TextView>(R.id.tvDate)
        val dateFormat = SimpleDateFormat("MMMM yyyy", Locale.getDefault())
        tvDate.text = dateFormat.format(Date())
    }
}
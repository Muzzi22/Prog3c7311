package com.example.flowfund

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import java.text.SimpleDateFormat
import java.util.*

class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        // Set current month and year dynamically
        findViewById<TextView>(R.id.tvDate)?.let {
            val dateFormat = SimpleDateFormat("MMMM yyyy", Locale.getDefault())
            it.text = dateFormat.format(Date())
        }

        // Opens the Add Expense screen — YOUR feature
        findViewById<Button>(R.id.btnGoToAddExpense)?.setOnClickListener {
            startActivity(Intent(this, AddExpenseActivity::class.java))
        }

        // Bottom nav — already on Home
        findViewById<LinearLayout>(R.id.navHome)?.setOnClickListener {
            // Already here, do nothing
        }

        // Bottom nav — go to Categories
        findViewById<LinearLayout>(R.id.navCategories)?.setOnClickListener {
            startActivity(Intent(this, CategoryActivity::class.java))
        }

        // Bottom nav — go to Goals (YOUR screen)
        findViewById<LinearLayout>(R.id.navGoals)?.setOnClickListener {
            startActivity(Intent(this, GoalsActivity::class.java))
        }

        // Bottom nav — go to Expense List (YOUR screen)
        findViewById<LinearLayout>(R.id.navExpenses)?.setOnClickListener {
            startActivity(Intent(this, ExpenseListActivity::class.java))
        }

        // Logout button
        findViewById<Button>(R.id.btnLogout)?.setOnClickListener {
            AlertDialog.Builder(this)
                .setTitle("Sign Out")
                .setMessage("Are you sure you want to sign out?")
                .setPositiveButton("Yes") { _, _ ->
                    val intent = Intent(this, MainActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or
                            Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                    finish()
                }
                .setNegativeButton("Cancel", null)
                .show()
        }
    }
}
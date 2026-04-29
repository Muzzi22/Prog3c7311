package com.example.flowfund

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.flowfund.databinding.ActivityExpenseListBinding
import java.util.Calendar

class ExpenseListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityExpenseListBinding
    private lateinit var adapter: ExpenseAdapter
    private lateinit var db: FlowFundDatabase

    private var startDate = ""
    private var endDate = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityExpenseListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = FlowFundDatabase.getDatabase(this)

        adapter = ExpenseAdapter(emptyList()) { photoPath ->
            val intent = Intent(this, PhotoViewActivity::class.java)
            intent.putExtra("photoPath", photoPath)
            startActivity(intent)
        }

        binding.recyclerExpenses.layoutManager = LinearLayoutManager(this)
        binding.recyclerExpenses.adapter = adapter

        binding.btnStartDate.setOnClickListener {
            showDatePicker { date ->
                startDate = date
                binding.btnStartDate.text = "Start: $date"
            }
        }

        binding.btnEndDate.setOnClickListener {
            showDatePicker { date ->
                endDate = date
                binding.btnEndDate.text = "End: $date"
            }
        }

        binding.btnSearch.setOnClickListener {
            if (startDate.isEmpty() || endDate.isEmpty()) {
                Toast.makeText(
                    this,
                    "Please select both start and end dates",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                loadExpenses()
            }
        }

        binding.navHome.setOnClickListener {
            val intent = Intent(this, HomeActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            startActivity(intent)
        }
    }

    private fun showDatePicker(onDateSelected: (String) -> Unit) {
        val calendar = Calendar.getInstance()
        DatePickerDialog(
            this, { _, year, month, day ->
                val date = String.format(
                    java.util.Locale.getDefault(),
                    "%04d-%02d-%02d",
                    year, month + 1, day
                )
                onDateSelected(date)
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        ).show()
    }

    private fun loadExpenses() {
        val expenses = db.expenseDao().getExpensesByPeriod(startDate, endDate)

        if (expenses.isEmpty()) {
            Toast.makeText(
                this,
                "No expenses found for this period",
                Toast.LENGTH_SHORT
            ).show()
        }
        adapter.updateExpenses(expenses)
    }
}
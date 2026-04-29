package com.example.flowfund

import android.os.Bundle
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import com.example.flowfund.databinding.ActivityMemberByronBinding


class MemberFeatureActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMemberByronBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMemberByronBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnGoToGoals.setOnClickListener {
            startActivity(Intent(this, GoalsActivity::class.java))
        }

        binding.btnGoToExpenseList.setOnClickListener {
            startActivity(Intent(this, ExpenseListActivity::class.java))
        }
    }
}
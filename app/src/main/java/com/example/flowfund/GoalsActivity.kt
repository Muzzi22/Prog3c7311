package com.example.flowfund

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.flowfund.databinding.ActivityGoalsBinding

class GoalsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityGoalsBinding
    private lateinit var db: FlowFundDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityGoalsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = FlowFundDatabase.getDatabase(this)
        loadCurrentGoals()

        binding.btnSaveGoals.setOnClickListener {
            saveGoals()
        }

        binding.navHome.setOnClickListener {
            val intent = Intent(this, HomeActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            startActivity(intent)
        }
    }

    private fun loadCurrentGoals() {
        val goal = db.goalDao().getLatestGoal()

        if (goal != null) {
            binding.tvCurrentMin.text = "Current minimum: R ${goal.minimumGoal}"
            binding.tvCurrentMax.text = "Current maximum: R ${goal.maximumGoal}"
        } else {
            binding.tvCurrentMin.text = "Current minimum: Not set"
            binding.tvCurrentMax.text = "Current maximum: Not set"
        }
    }

    private fun saveGoals() {
        val minText = binding.edtMinGoal.text.toString().trim()
        val maxText = binding.edtMaxGoal.text.toString().trim()

        if (minText.isEmpty()) {
            binding.edtMinGoal.error = "Please enter a minimum goal"
            return
        }

        if (maxText.isEmpty()) {
            binding.edtMaxGoal.error = "Please enter a maximum goal"
            return
        }

        val min = minText.toDoubleOrNull()
        val max = maxText.toDoubleOrNull()

        if (min == null || min < 0) {
            binding.edtMinGoal.error = "Please enter a valid amount"
            return
        }

        if (max == null || max < 0) {
            binding.edtMaxGoal.error = "Please enter a valid amount"
            return
        }

        if (min >= max) {
            Toast.makeText(
                this,
                "Minimum goal must be less than maximum goal",
                Toast.LENGTH_SHORT
            ).show()
            return
        }

        db.goalDao().insertGoal(Goal(minimumGoal = min, maximumGoal = max))

        Toast.makeText(this, "Goals saved successfully!", Toast.LENGTH_SHORT).show()

        binding.tvCurrentMin.text = "Current minimum: R $min"
        binding.tvCurrentMax.text = "Current maximum: R $max"

        binding.edtMinGoal.text.clear()
        binding.edtMaxGoal.text.clear()
    }
}
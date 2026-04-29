package com.example.flowfund

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.flowfund.databinding.ItemExpenseBinding

class ExpenseAdapter(
    private var expenses: List<Expense>,
    private val onPhotoClick: (String) -> Unit
) : RecyclerView.Adapter<ExpenseAdapter.ExpenseViewHolder>() {


    inner class ExpenseViewHolder(val binding: ItemExpenseBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExpenseViewHolder {
        val binding = ItemExpenseBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ExpenseViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ExpenseViewHolder, position: Int) {
        val expense = expenses[position]


        holder.binding.tvItemCategory.text = expense.category
        holder.binding.tvItemDate.text = expense.date
        holder.binding.tvItemTime.text = "${expense.startTime} - ${expense.endTime}"
        holder.binding.tvItemDescription.text = expense.description
        holder.binding.tvItemAmount.text = "- R ${expense.amount}"

        if (!expense.photoPath.isNullOrEmpty()) {
            holder.binding.btnViewPhoto.visibility = View.VISIBLE
            holder.binding.btnViewPhoto.setOnClickListener {
                onPhotoClick(expense.photoPath)
            }
        } else {
            holder.binding.btnViewPhoto.visibility = View.GONE
        }
    }

    override fun getItemCount() = expenses.size


    fun updateExpenses(newExpenses: List<Expense>) {
        expenses = newExpenses
        notifyDataSetChanged()
    }
}

package com.example.flowfund

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

//Data Access Object
@Dao
interface ExpenseDao {

//    Save new expense
    @Insert
    suspend fun insertExpense(expense: Expense)

//     Get all expenses
    @Query("SELECT * FROM expenses ORDER BY id DESC")
    suspend fun getAllExpenses(): List<Expense>


    @Query("SELECT * FROM expenses WHERE category = :category ORDER BY id DESC")
    suspend fun getExpensesByCategory(category: String): List<Expense>

    @Query("SELECT * FROM expenses WHERE date BETWEEN :start AND :end")
    fun getExpensesByPeriod(start: String, end: String): List<Expense>
}
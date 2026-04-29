package com.example.flowfund

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(entities = [Expense::class], version = 1, exportSchema = false)
abstract class FlowFundDatabase : RoomDatabase() {

    abstract fun expenseDao(): ExpenseDao

    companion object {

        @Volatile
        private var INSTANCE: FlowFundDatabase? = null

        fun getDatabase(context: Context): FlowFundDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    FlowFundDatabase::class.java,
                    "flowfund_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
package com.example.flowfund

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(entities = [Expense::class, Goal::class], version = 2, exportSchema = false)
abstract class FlowFundDatabase : RoomDatabase() {

    abstract fun expenseDao(): ExpenseDao

    abstract fun goalDao(): GoalDao

    companion object {

        @Volatile
        private var INSTANCE: FlowFundDatabase? = null

        fun getDatabase(context: Context): FlowFundDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    FlowFundDatabase::class.java,
                    "flowfund_database"
                )
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
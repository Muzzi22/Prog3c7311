package com.example.flowfund

import androidx.room.*

@Dao
interface GoalDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertGoal(goal: Goal)

    @Update
    fun updateGoal(goal: Goal)

    @Delete
    fun deleteGoal(goal: Goal)

    @Query("SELECT * FROM goals")
    fun getAllGoals(): List<Goal>


    @Query("SELECT * FROM goals ORDER BY id DESC LIMIT 1")
    fun getLatestGoal(): Goal?

    @Query("SELECT MIN(minimumGoal) FROM goals")
    fun minimumGoal(): Double?

    @Query("SELECT MAX(maximumGoal) FROM goals")
    fun maximumGoal(): Double?
}
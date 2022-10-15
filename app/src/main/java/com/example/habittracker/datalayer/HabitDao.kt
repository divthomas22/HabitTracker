package com.example.habittracker.datalayer

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface HabitDao {

    @Insert( onConflict = OnConflictStrategy.REPLACE)
    fun saveHabit(habit: Habit) : Void

    @Delete
    fun deleteHabit(habit: Habit) : Void

    @Update
    fun editHabit(habit: Habit) : Void

    @Query("SELECT count(*) From habits")
    fun count(): LiveData<Int>

    @Query("SELECT * FROM habits")
    fun getAllHabits(): LiveData<List<Habit>>

    @Query("SELECT * FROM habits where id = :habitId")
    fun searchHabit(habitId: Int): LiveData<Habit>

    @Query("SELECT * FROM habits WHERE name like :habitName ")
    fun searchHabitsbyName(habitName:String): LiveData<List<Habit>>
}



package com.example.habittracker

import androidx.lifecycle.LiveData
import com.example.habittracker.datalayer.Habit
import com.example.habittracker.datalayer.HabitDao
import java.util.concurrent.Executors

class HabitTrackerRepository (private val habitDao: HabitDao) {
    val executor =  Executors.newSingleThreadExecutor()

    fun addHabit(habit: Habit){
        executor.execute { habitDao.saveHabit(habit) }
    }

    fun deleteHabit(habit: Habit) {
        executor.execute { habitDao.deleteHabit(habit) }
    }

    fun editHabit(habit: Habit) {
        executor.execute { habitDao.editHabit(habit) }
    }

    fun searchHabitById(habit: Habit) {
        executor.execute {
        habitDao.searchHabit(habit.id)
        }
    }

    fun getAllHabits(): LiveData<List<Habit>> {
        return habitDao.getAllHabits()
    }

    fun searchHabitByName(habit: Habit): LiveData<List<Habit>> {
        return habitDao.searchHabitsbyName(habit.name)
    }

    fun count(): LiveData<Int> {
        return habitDao.count()
    }
}
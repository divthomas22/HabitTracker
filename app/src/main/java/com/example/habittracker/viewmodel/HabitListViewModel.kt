package com.example.habittracker.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.habittracker.HabitTrackerApplication
import com.example.habittracker.datalayer.Habit
import java.util.concurrent.Executors

class HabitListViewModel(application: Application):
    AndroidViewModel(application) {
    // pass the habitTrackerApplication as a parameter
    // make sure to define the application name in the manifest file.
    val habitTrackerDatabase = (application as HabitTrackerApplication).habitsDatabase
    val habitDao = habitTrackerDatabase.habitDao()

    private val _habitList: LiveData<List<Habit>> = habitDao.getAllHabits()

    val habitList:LiveData<List<Habit>>
        get() = _habitList

    fun getAllHabits(): LiveData<List<Habit>> {
        return habitDao.getAllHabits()
    }

    fun addHabit(habit: Habit) {
        Executors.newSingleThreadExecutor().execute {
            habitDao.saveHabit(habit)
        }
    }
}
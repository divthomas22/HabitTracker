package com.example.habittracker

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.habittracker.datalayer.HabitsDatabase
import java.util.concurrent.Executors

class HabitTrackerApplication : Application() {
    lateinit var habitsDatabase: HabitsDatabase
    lateinit var habitTrackerRepository: HabitTrackerRepository
    override fun onCreate() {
        super.onCreate()

        habitsDatabase = Room.databaseBuilder(
            applicationContext, HabitsDatabase::class.java,
            "habittracker-db"
        )
            .addCallback(object : RoomDatabase.Callback() {
            })
            .build()

        habitTrackerRepository = HabitTrackerRepository(habitsDatabase.habitDao())
    }


}
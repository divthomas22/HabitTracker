package com.example.habittracker.datalayer

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [Habit::class],
    version = 2
)
abstract class HabitsDatabase : RoomDatabase() {
    abstract fun habitDao(): HabitDao

}


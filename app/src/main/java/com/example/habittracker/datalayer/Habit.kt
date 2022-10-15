package com.example.habittracker.datalayer

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity (tableName = "habits")
data class Habit(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    var name: String,
    var description: String,
    var priority: Float,
    @ColumnInfo(name = "reminder")
    var remindTime: String,
    var isComplete: Boolean,
    var streak: Int) {

    override fun toString(): String {
        return "Habit(id=$id, name='$name', description='$description', priority=$priority, remindTime='$remindTime', isComplete=$isComplete, streak=$streak)"
    }
}

package com.example.habittracker.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.room.ColumnInfo
import com.example.habittracker.HabitTrackerApplication
import com.example.habittracker.datalayer.Habit
import kotlinx.coroutines.launch

class CurHabitViewModel(application: Application): AndroidViewModel(application){
    private val _curHabit: MutableLiveData<Habit> = MutableLiveData()
    val curHabit: LiveData<Habit>
        get() = _curHabit

    val habitTrackerRepository =
        (application as HabitTrackerApplication).habitTrackerRepository

    fun initCurHabit(habit: Habit){
        if (_curHabit.value == null)
            _curHabit.value = habit
    }

    fun setCurHabit(habit: Habit){
        _curHabit.value = habit
    }

    fun updateCurHabit(
        name: String,
        description: String,
        priority: Float,
        remindTime: String,
        isComplete: Boolean,
        streak: Int
    ){
        _curHabit.value = _curHabit.value?.apply{
            this.name = name
            this.description = description
            this.priority = priority
            this.remindTime = remindTime
            this.isComplete = isComplete
            this.streak = streak
        }
        habitTrackerRepository.editHabit(_curHabit.value!!)
    }

}
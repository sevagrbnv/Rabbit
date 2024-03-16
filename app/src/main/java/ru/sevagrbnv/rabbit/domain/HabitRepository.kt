package ru.sevagrbnv.rabbit.domain

import androidx.lifecycle.LiveData

interface HabitRepository {

    fun addHabit(habit: Habit)

    fun getHabit(habitId: Long): Habit?

    fun editHabit(habit: Habit)

    fun getAllHabits(): LiveData<List<Habit>>

    fun getHabitsByType(type: String): LiveData<List<Habit>>

}
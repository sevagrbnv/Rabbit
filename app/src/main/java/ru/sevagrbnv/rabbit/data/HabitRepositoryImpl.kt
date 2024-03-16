package ru.sevagrbnv.rabbit.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.map
import ru.sevagrbnv.rabbit.domain.Habit
import ru.sevagrbnv.rabbit.domain.HabitRepository

object HabitRepositoryImpl : HabitRepository {

    private val habitDtoList = MutableLiveData<MutableList<HabitDto>>(mutableListOf())
    override fun addHabit(habit: Habit) {
        val list = habitDtoList.value ?: mutableListOf()
        list.add(habit.toHabitDto())
        habitDtoList.value = list
    }

    override fun getHabit(habitId: Long): Habit? {
        return habitDtoList.value?.find { habitDto -> habitId == habitDto.id }?.toHabit()
    }

    override fun editHabit(habit: Habit) {
        val oldHabitId = habitDtoList.value?.indexOf(habitDtoList.value?.find { it.id == habit.id })
        val list = habitDtoList.value ?: mutableListOf()
        if (oldHabitId != null)
            list.set(oldHabitId, habit.toHabitDto())
        habitDtoList.value = list
    }

    override fun getAllHabits(): LiveData<List<Habit>> =
        habitDtoList.map {
            it.toListHabit()
        }

    override fun getHabitsByType(type: String): LiveData<List<Habit>> =
        habitDtoList.map { it.toListHabit().filter { it.type == type } }

}
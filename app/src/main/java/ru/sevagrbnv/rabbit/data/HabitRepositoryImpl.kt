package ru.sevagrbnv.rabbit.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.map
import ru.sevagrbnv.rabbit.domain.Habit
import ru.sevagrbnv.rabbit.domain.HabitRepository

object HabitRepositoryImpl : HabitRepository {

    private val habitDtoList = MutableLiveData<MutableList<HabitDto>>(mutableListOf())
    override fun addHabit(habit: Habit) {
        val list = habitDtoList.value
        list?.add(habit.toHabitDto())
        habitDtoList.value = list
    }

    override fun getHabit(habitId: Long): Habit? {
        return habitDtoList.value?.find { habitDto -> habitId == habitDto.id }?.toHabit()
    }

    override fun editHabit(habit: Habit) {
        val oldHabitId = habitDtoList.value?.indexOf(habitDtoList.value?.find { it.id == habit.id })
        val list = habitDtoList.value
        if (oldHabitId != null)
            list?.set(oldHabitId, habit.toHabitDto())
        habitDtoList.value = list
    }

    override fun getAllHabits(): LiveData<List<Habit>> =
        habitDtoList.map {
            it.toListHabit()
        }

    override fun getBadHabits(): LiveData<List<Habit>> =
        MutableLiveData<List<HabitDto>>(habitDtoList.value?.filter { it.type == "Bad" }).map {
            it.toListHabit()
        }

    override fun getGoodHabits(): LiveData<List<Habit>> =
        MutableLiveData<List<HabitDto>>(habitDtoList.value?.filter { it.type == "Good" }).map {
            it.toListHabit()
        }
}
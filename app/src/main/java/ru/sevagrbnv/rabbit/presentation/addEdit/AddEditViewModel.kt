package ru.sevagrbnv.rabbit.presentation.addEdit

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.sevagrbnv.rabbit.data.HabitRepositoryImpl
import ru.sevagrbnv.rabbit.domain.Habit

class AddEditViewModel : ViewModel() {

    private val _habit = MutableLiveData<Habit>()
    val habit: LiveData<Habit>
        get() = _habit

    fun getHabit(habitId: Long) {
        val item = HabitRepositoryImpl.getHabit(habitId)
        _habit.value = item
    }

    fun addHabit(
        title: String,
        description: String,
        priority: String,
        type: String,
        count: String,
        period: String,
        color: Int
    ) {
        HabitRepositoryImpl.addHabit(
            Habit(
                id = System.currentTimeMillis(),
                title = title,
                description = description,
                priority = priority,
                type = type,
                count = count.toInt(),
                period = period,
                color = color
            )
        )
        finishWork()
    }

    fun editHabit(
        title: String,
        description: String,
        priority: String,
        type: String,
        count: String,
        period: String,
        color: Int
    ) {

        val item = habit.value?.copy(
            title = title,
            description = description,
            priority = priority,
            type = type,
            count = count.toInt(),
            period = period,
            color = color
        )
        if (item != null) {
            HabitRepositoryImpl.editHabit(item)
        }
        finishWork()
    }

    private val _shouldCloseScreen = MutableLiveData<Unit>()
    val shouldCloseScreen: LiveData<Unit>
        get() = _shouldCloseScreen

    private fun finishWork() {
        _shouldCloseScreen.value = Unit
    }
}
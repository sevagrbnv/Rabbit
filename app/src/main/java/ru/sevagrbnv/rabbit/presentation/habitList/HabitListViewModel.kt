package ru.sevagrbnv.rabbit.presentation.habitList

import androidx.lifecycle.ViewModel
import ru.sevagrbnv.rabbit.data.HabitRepositoryImpl

class HabitListViewModel : ViewModel() {

    var type: String? = null

    val habitList by lazy { HabitRepositoryImpl.getHabitsByType(type ?: "Good") }
}
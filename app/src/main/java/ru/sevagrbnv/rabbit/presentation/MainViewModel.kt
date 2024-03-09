package ru.sevagrbnv.rabbit.presentation

import androidx.lifecycle.ViewModel
import ru.sevagrbnv.rabbit.data.HabitRepositoryImpl

class MainViewModel: ViewModel() {

    val habitList = HabitRepositoryImpl.getAllHabits()
}
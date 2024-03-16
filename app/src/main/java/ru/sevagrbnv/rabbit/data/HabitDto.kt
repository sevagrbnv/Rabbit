package ru.sevagrbnv.rabbit.data

data class HabitDto(
    val id: Long = 0,
    val title: String,
    val description: String,
    val priority: String,
    val type: String,
    val period: String,
    val count: Int,
    val color: Int
)
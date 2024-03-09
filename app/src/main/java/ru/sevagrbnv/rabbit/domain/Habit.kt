package ru.sevagrbnv.rabbit.domain

data class Habit(
    val id: Long = 0,
    val title: String,
    val description: String,
    val priority: String,
    val type: String,
    val count: Int,
    val period: String,
    val color: Int
)

enum class Type {
    BAD,
    GOOD,
    NO
}
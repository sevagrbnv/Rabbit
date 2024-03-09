package ru.sevagrbnv.rabbit.data

import ru.sevagrbnv.rabbit.domain.Habit
fun HabitDto.toHabit() = Habit(
    id = this.id,
    title = this.title,
    description = this.description,
    priority = this.priority,
    type = this.type,
    period = this.period,
    count = this.count,
    color = this.color
)

fun Habit.toHabitDto() = HabitDto(
    id = this.id,
    title = this.title,
    description = this.description,
    priority = this.priority,
    type = this.type,
    period = this.period,
    count = this.count,
    color = this.color
)

fun List<HabitDto>.toListHabit() = this.map { item -> item.toHabit()}

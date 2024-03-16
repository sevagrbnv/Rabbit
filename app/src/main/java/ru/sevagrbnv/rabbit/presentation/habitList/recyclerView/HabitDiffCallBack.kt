package ru.sevagrbnv.rabbit.presentation.habitList.recyclerView

import androidx.recyclerview.widget.DiffUtil
import ru.sevagrbnv.rabbit.domain.Habit

class HabitDiffCallBack: DiffUtil.ItemCallback<Habit>() {

    override fun areItemsTheSame(oldItem: Habit, newItem: Habit): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Habit, newItem: Habit): Boolean {
        return oldItem == newItem
    }
}
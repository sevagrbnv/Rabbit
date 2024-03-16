package ru.sevagrbnv.rabbit.presentation.recyclerView

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.ListAdapter
import ru.sevagrbnv.rabbit.R
import ru.sevagrbnv.rabbit.databinding.HabitListItemBinding
import ru.sevagrbnv.rabbit.domain.Habit

class HabitListAdapter: ListAdapter<Habit, HabitViewHolder>(
    HabitDiffCallBack()
) {

    var onItemClickListener: ((Habit) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HabitViewHolder {
        val layout = R.layout.habit_list_item
        val binding = DataBindingUtil.inflate<ViewDataBinding>(
            LayoutInflater.from(parent.context),
            layout,
            parent,
            false
        )
        return HabitViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HabitViewHolder, position: Int) {
        val item = getItem(position)
        val binding = holder.binding as HabitListItemBinding
        binding.itemTitle.text = item.title
        binding.itemDescription.text = item.description
        binding.itemPriority.text = item.priority.toString()
        binding.itemType.text = item.type.toString()
        binding.itemPeriod.text = "${item.count} in ${item.period}"
        binding.root.setBackgroundColor(item.color)
        binding.root.setOnClickListener {
            onItemClickListener?.invoke(item)
        }
    }
}
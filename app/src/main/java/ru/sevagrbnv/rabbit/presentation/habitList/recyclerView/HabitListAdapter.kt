package ru.sevagrbnv.rabbit.presentation.habitList.recyclerView

import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.ListAdapter
import ru.sevagrbnv.rabbit.R
import ru.sevagrbnv.rabbit.databinding.HabitListItemBinding
import ru.sevagrbnv.rabbit.domain.Habit

class HabitListAdapter : ListAdapter<Habit, HabitViewHolder>(
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
        binding.itemPriority.text = item.priority
        binding.itemType.text = item.type
        binding.itemPeriod.text =
            holder.itemView.context.getString(R.string.count_period, item.count, item.period)
        binding.color.backgroundTintList = ColorStateList.valueOf(item.color)
        binding.root.setOnClickListener {
            onItemClickListener?.invoke(item)
        }
    }
}
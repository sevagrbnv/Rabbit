package ru.sevagrbnv.rabbit.presentation.viewPager

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import ru.sevagrbnv.rabbit.presentation.habitList.HabitListFragment
import ru.sevagrbnv.rabbit.presentation.habitList.HabitListFragment.Companion.BAD_TYPE
import ru.sevagrbnv.rabbit.presentation.habitList.HabitListFragment.Companion.GOOD_TYPE

class HabitViewPagerAdapter(
    fragmentActivity: FragmentActivity
) : FragmentStateAdapter(fragmentActivity) {

    override fun createFragment(position: Int): Fragment {

        val type = when (position) {
            0 -> GOOD_TYPE
            1 -> BAD_TYPE
            else -> throw IllegalArgumentException("Invalid position")
        }
        return HabitListFragment.getFragment(type)
    }

    override fun getItemCount() = 2
}
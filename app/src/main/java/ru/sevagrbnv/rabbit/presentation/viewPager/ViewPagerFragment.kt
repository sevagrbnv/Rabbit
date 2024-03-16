package ru.sevagrbnv.rabbit.presentation.viewPager

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.floatingactionbutton.FloatingActionButton
import ru.sevagrbnv.rabbit.R
import ru.sevagrbnv.rabbit.presentation.addEdit.AddEditFragment.Companion.ADD_MODE

class ViewPagerFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_view_pager, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val viewPager = view.findViewById<ViewPager2>(R.id.viewPager)
        val adapter = HabitViewPagerAdapter(requireActivity())
        viewPager.adapter = adapter

        val fab = view.findViewById<FloatingActionButton>(R.id.fab)
        fab.setOnClickListener {
            val destination = ViewPagerFragmentDirections.actionViewPagerFragmentToEditFragment(ADD_MODE)
            findNavController().navigate(destination)
        }
    }
}
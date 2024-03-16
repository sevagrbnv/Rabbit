package ru.sevagrbnv.rabbit.presentation.habitList

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import ru.sevagrbnv.rabbit.databinding.FragmentHabitListBinding
import ru.sevagrbnv.rabbit.presentation.addEdit.AddEditFragment.Companion.EDIT_MODE
import ru.sevagrbnv.rabbit.presentation.habitList.recyclerView.HabitListAdapter
import ru.sevagrbnv.rabbit.presentation.viewPager.ViewPagerFragmentDirections

class HabitListFragment : Fragment() {

    private val binding by lazy {
        FragmentHabitListBinding.inflate(layoutInflater)
    }

    private val viewModel by viewModels<HabitListViewModel>()

    private var habitListAdapter: HabitListAdapter? = null

    private var habitType: String = "NO"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = binding.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        parseParams()
        viewModel.type = habitType
        viewModel.habitList.observe(viewLifecycleOwner) {
            habitListAdapter?.submitList(it)
        }
        setRecView()
    }

    private fun setRecView() {
        habitListAdapter = HabitListAdapter()
        binding.recyclerView.adapter = habitListAdapter
        setItemClickListener()
    }

    private fun setItemClickListener() {
        habitListAdapter?.onItemClickListener = {
            val direction = ViewPagerFragmentDirections.actionViewPagerFragmentToEditFragment(
                itemId = it.id,
                screenMode = EDIT_MODE
            )
            findNavController().navigate(direction)
        }
    }

    private fun parseParams() {
        val args = requireArguments()
        if (!args.containsKey(FRAGMENT_TYPE))
            throw RuntimeException("Fragment type not found")
        val type = args.getString(FRAGMENT_TYPE)
        if (type != GOOD_TYPE && type != BAD_TYPE)
            throw RuntimeException("Unknown type $type")
        habitType = type
    }

    companion object {

        const val FRAGMENT_TYPE = "FRAGMENT_TYPE"
        const val GOOD_TYPE = "Good"
        const val BAD_TYPE = "Bad"
        fun getFragment(type: String) =
            HabitListFragment().apply {
                arguments = Bundle().apply {
                    putString(FRAGMENT_TYPE, type)
                }
            }
    }
}
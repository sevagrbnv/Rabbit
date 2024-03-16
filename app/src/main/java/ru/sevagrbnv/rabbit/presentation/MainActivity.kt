package ru.sevagrbnv.rabbit.presentation

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import ru.sevagrbnv.rabbit.databinding.ActivityMainBinding
import ru.sevagrbnv.rabbit.presentation.EditActivity.Companion.ADD_MODE
import ru.sevagrbnv.rabbit.presentation.EditActivity.Companion.EDIT_MODE
import ru.sevagrbnv.rabbit.presentation.EditActivity.Companion.ITEM_ID
import ru.sevagrbnv.rabbit.presentation.EditActivity.Companion.SCREEN_MODE
import ru.sevagrbnv.rabbit.presentation.recyclerView.HabitListAdapter

class MainActivity : AppCompatActivity() {

    private var binding: ActivityMainBinding? = null
    private var viewModel: MainViewModel? = null
    private var habitListAdapter: HabitListAdapter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)


        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        viewModel?.habitList?.observe(this) {
            habitListAdapter?.submitList(it)
        }
        setRecView()
        binding?.fab?.setOnClickListener {
            startActivity(
                Intent(this, EditActivity::class.java)
                    .putExtra(SCREEN_MODE, ADD_MODE)
            )
        }
    }

    private fun setRecView() {
        habitListAdapter = HabitListAdapter()
        binding?.recyclerView?.adapter = habitListAdapter
        setItemClickListener()
    }

    private fun setItemClickListener() {
        habitListAdapter?.onItemClickListener = {
            startActivity(Intent(this, EditActivity::class.java)
                .putExtra(SCREEN_MODE, EDIT_MODE)
                .putExtra(ITEM_ID, it.id))
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
        viewModel = null
        habitListAdapter = null
    }

}
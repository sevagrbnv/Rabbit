package ru.sevagrbnv.rabbit.presentation

import android.content.res.ColorStateList
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.ArrayAdapter
import android.widget.RadioButton
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.forEachIndexed
import androidx.lifecycle.ViewModelProvider
import ru.sevagrbnv.rabbit.R
import ru.sevagrbnv.rabbit.databinding.ActivityEditBinding

class EditActivity : AppCompatActivity() {

    private var binding: ActivityEditBinding? = null
    private var viewModel: EditViewModel? = null
    private val gradientColorPicker by lazy { GradientColorPicker(16) }

    private var screenMode = UNKNOWN_MODE
    private var itemId = UNDEFINED_ID

    private var selectedColor = gradientColorPicker.getColorArray()[0]
    private val values by lazy {
        listOf(
            getString(R.string.no_priority),
            getString(R.string.low_priority),
            getString(R.string.medium_priority),
            getString(R.string.high_priority)
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        setSupportActionBar(binding?.toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        viewModel = ViewModelProvider(this)[EditViewModel::class.java]

        setSpinner()
        setColorPicker()
        parseIntent()
        startRightMode()
        viewModel?.shouldCloseScreen?.observe(this) { finish() }
    }

    private fun setColorPicker() {
        binding?.colorPicker?.background = GradientColorPicker.gradient
        binding?.colorPicker?.forEachIndexed { index, item ->
            item.backgroundTintList =
                ColorStateList.valueOf(gradientColorPicker.getColorArray()[index])
        }
        binding?.colorPicker?.setOnCheckedChangeListener { _, checkedId ->
            val selectedRadioButton: View? = binding?.colorPicker?.findViewById(checkedId)
            selectedColor = gradientColorPicker.getColorArray()[binding?.colorPicker?.indexOfChild(
                selectedRadioButton
            ) ?: 0]
        }
    }

    private fun setSpinner() {
        val adapter = ArrayAdapter(
            this, android.R.layout.simple_spinner_item,
            values
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding?.spinner?.adapter = adapter
    }

    private fun startRightMode() {
        when (screenMode) {
            ADD_MODE -> {
                startAddMode()
            }

            EDIT_MODE -> {
                startEditMode()
            }
        }
    }

    private fun startAddMode() {
        binding?.toolbarTitle?.text = getString(R.string.add_mode)
        binding?.saveButton?.setOnClickListener {
            viewModel?.addHabit(
                title = binding?.editTextTitle?.text.toString(),
                description = binding?.editTextDescription?.text.toString(),
                priority = binding?.spinner?.selectedItem.toString(),
                type = when (binding?.typePicker?.checkedRadioButtonId) {
                    binding?.radioGood?.id -> getString(R.string.good)
                    binding?.radioBad?.id -> getString(R.string.bad)
                    else -> getString(R.string.no)
                },
                count = binding?.editTextCount?.text.toString(),
                period = binding?.editTextPeriod?.text.toString(),
                color = selectedColor
            )
        }
    }

    private fun startEditMode() {
        binding?.toolbarTitle?.text = getString(R.string.edit_mode)
        viewModel?.getHabit(itemId)
        viewModel?.habit?.observe(this) {
            binding?.editTextTitle?.setText(it.title)
            binding?.editTextDescription?.setText(it.description)
            binding?.spinner?.setSelection(values.indexOf(it.priority))
            if (it.type == getString(R.string.good))
                binding?.radioGood?.isChecked = true
            else binding?.radioBad?.isChecked = true
            binding?.editTextCount?.setText(it.count.toString())
            binding?.editTextPeriod?.setText(it.period)
            binding?.colorPicker?.forEachIndexed { index, item ->
                (item as RadioButton).isChecked =
                    it.color == gradientColorPicker.getColorArray()[index]
            }
        }
        binding?.saveButton?.setOnClickListener {
            viewModel?.editHabit(
                title = binding?.editTextTitle?.text.toString(),
                description = binding?.editTextDescription?.text.toString(),
                priority = binding?.spinner?.selectedItem.toString(),
                type = when (binding?.typePicker?.checkedRadioButtonId) {
                    binding?.radioGood?.id -> getString(R.string.good)
                    binding?.radioBad?.id -> getString(R.string.bad)
                    else -> getString(R.string.no)
                },
                count = binding?.editTextCount?.text.toString(),
                period = binding?.editTextPeriod?.text.toString(),
                color = selectedColor
            )
        }
    }

    private fun parseIntent() {
        val args = intent?.extras
        screenMode = args?.getString(SCREEN_MODE) ?: throw RuntimeException("Unknown screen mode")
        if (screenMode == EDIT_MODE) {
            itemId = args.getLong(ITEM_ID)
        }
    }

    companion object {
        const val ITEM_ID = "ITEM_ID"
        private const val UNDEFINED_ID = -1L

        const val SCREEN_MODE = "SCREEN_MODE"
        const val EDIT_MODE = "EDIT_MODE"
        const val ADD_MODE = "ADD_MODE"
        private const val UNKNOWN_MODE = ""
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
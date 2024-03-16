package ru.sevagrbnv.rabbit.presentation.addEdit

import android.content.res.ColorStateList
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.RadioButton
import androidx.core.view.forEachIndexed
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import ru.sevagrbnv.rabbit.R
import ru.sevagrbnv.rabbit.databinding.FragmentEditBinding

class AddEditFragment : Fragment() {

    private val binding by lazy { FragmentEditBinding.inflate(layoutInflater) }
    private val viewModel by viewModels<AddEditViewModel>()
    private val gradientColorPicker by lazy { GradientColorPicker(16) }

    private val values by lazy {
        listOf(
            getString(R.string.no_priority),
            getString(R.string.low_priority),
            getString(R.string.medium_priority),
            getString(R.string.high_priority)
        )
    }

    private var screenMode = UNKNOWN_MODE
    private var itemId = UNDEFINED_ID
    private var selectedColor = gradientColorPicker.getColorArray()[0]
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setSpinner()
        setColorPicker()
        parseIntent()
        startRightMode()
        viewModel.shouldCloseScreen.observe(viewLifecycleOwner) {
            findNavController().popBackStack()
        }
    }

    private fun setColorPicker() {
        with(binding.colorPicker) {
            background = GradientColorPicker.gradient
            forEachIndexed { index, item ->
                item.backgroundTintList =
                    ColorStateList.valueOf(gradientColorPicker.getColorArray()[index])
            }
            setOnCheckedChangeListener { _, checkedId ->
                val selectedRadioButton: View? = binding.colorPicker.findViewById(checkedId)
                selectedColor =
                    gradientColorPicker.getColorArray()[binding.colorPicker.indexOfChild(
                        selectedRadioButton
                    )]
            }
        }
    }

    private fun setSpinner() {
        val adapter = ArrayAdapter(
            requireContext(), android.R.layout.simple_spinner_item,
            values
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinner.adapter = adapter
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
        binding.saveButton.setOnClickListener {
            viewModel.addHabit(
                title = binding.editTextTitle.text.toString(),
                description = binding.editTextDescription.text.toString(),
                priority = binding.spinner.selectedItem.toString(),
                type = when (binding.typePicker.checkedRadioButtonId) {
                    binding.radioGood.id -> getString(R.string.good)
                    binding.radioBad.id -> getString(R.string.bad)
                    else -> getString(R.string.no)
                },
                count = binding.editTextCount.text.toString(),
                period = binding.editTextPeriod.text.toString(),
                color = selectedColor
            )
        }
    }

    private fun startEditMode() {
        viewModel.getHabit(itemId)

        viewModel.habit.observe(viewLifecycleOwner) {
            with(binding) {
                editTextTitle.setText(it.title)
                editTextDescription.setText(it.description)
                spinner.setSelection(values.indexOf(it.priority))
                radioGood.isChecked = it.type == getString(R.string.good)
                radioBad.isChecked = it.type == getString(R.string.bad)
                editTextCount.setText(it.count.toString())
                editTextPeriod.setText(it.period)
                colorPicker.forEachIndexed { index, item ->
                    (item as RadioButton).isChecked =
                        it.color == gradientColorPicker.getColorArray()[index]
                }
            }
        }

        binding.saveButton.setOnClickListener {
            viewModel.editHabit(
                title = binding.editTextTitle.text.toString(),
                description = binding.editTextDescription.text.toString(),
                priority = binding.spinner.selectedItem.toString(),
                type = when (binding.typePicker.checkedRadioButtonId) {
                    binding.radioGood.id -> getString(R.string.good)
                    binding.radioBad.id -> getString(R.string.bad)
                    else -> getString(R.string.no)
                },
                count = binding.editTextCount.text.toString(),
                period = binding.editTextPeriod.text.toString(),
                color = selectedColor
            )
        }
    }

    private fun parseIntent() {
        screenMode = AddEditFragmentArgs.fromBundle(requireArguments()).screenMode
        if (screenMode == EDIT_MODE) {
            itemId = AddEditFragmentArgs.fromBundle(requireArguments()).itemId
        }
    }

    companion object {
        private const val UNDEFINED_ID = -1L

        const val EDIT_MODE = "EDIT_MODE"
        const val ADD_MODE = "ADD_MODE"
        private const val UNKNOWN_MODE = ""
    }
}
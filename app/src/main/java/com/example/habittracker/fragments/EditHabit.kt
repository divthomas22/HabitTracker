package com.example.habittracker.fragments
import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.RatingBar
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.habittracker.R
import com.example.habittracker.databinding.EditHabitBinding
import com.example.habittracker.viewmodel.CurHabitViewModel
import com.example.habittracker.viewmodel.HabitListViewModel

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class EditHabit : Fragment(), View.OnClickListener {

    private var _binding: EditHabitBinding? = null
    private lateinit var viewModel: CurHabitViewModel
    private lateinit var listViewModel: HabitListViewModel
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = EditHabitBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        listViewModel =
            ViewModelProvider(requireActivity()).get(HabitListViewModel::class.java)
        viewModel =
            ViewModelProvider(requireActivity()).get(CurHabitViewModel::class.java)

        viewModel.curHabit.observe(viewLifecycleOwner, Observer {
            binding.nameEdit.setText(it.name)
            binding.descEdit.setText(it.description)
            binding.editPriority.rating = it.priority
            binding.editReminder.setText(it.remindTime)
        })

        binding.save.setOnClickListener (this)
        binding.delete.setOnClickListener (this)

        Log.d("Habit Edit", viewModel.curHabit.value.toString())
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onClick(view: View) {
        val habit = viewModel.curHabit.value
        if (habit != null) {
            if (view.id == R.id.save) {
                viewModel.updateCurHabit(
                binding.nameEdit.text.toString(),
                binding.descEdit.text.toString(),
                binding.editPriority.rating,
                binding.editReminder.text.toString(),
                    habit.isComplete,
                    habit.streak
                )
                Log.d(TAG, "Habit updated at id ${habit.id}: \n" + viewModel.curHabit.value?.toString())
            }

            if (view.id == R.id.delete) {
                Log.d(TAG, "Deleting Habit ID: ${habit.id}")
                viewModel.habitTrackerRepository.deleteHabit(habit)
                Log.d(TAG, "HABIT ${habit.id} DELETED")
            }
        } else { Log.d(TAG, "NO HABIT SELECTED") }

        Log.d(TAG, "Navigating back to Habit List...")
        findNavController().navigate(R.id.action_ThirdFragment_to_FirstFragment)
    }
}
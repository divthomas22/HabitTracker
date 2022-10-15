package com.example.habittracker.fragments

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.RatingBar
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.habittracker.R
import com.example.habittracker.databinding.CreateHabitBinding
import com.example.habittracker.datalayer.Habit
import com.example.habittracker.viewmodel.CurHabitViewModel
import com.example.habittracker.viewmodel.HabitListViewModel

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class CreateHabit : Fragment(), View.OnClickListener {

    private var _binding: CreateHabitBinding? = null

    private lateinit var listViewModel: HabitListViewModel
    private lateinit var viewModel: CurHabitViewModel

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {



        _binding = CreateHabitBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        listViewModel =
            ViewModelProvider(requireActivity()).get(HabitListViewModel::class.java)
        viewModel =
            ViewModelProvider(requireActivity()).get(CurHabitViewModel::class.java)

        binding.submit.setOnClickListener (this)
        binding.cancel.setOnClickListener (this)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onClick(view: View) {
        if (view.id == R.id.submit){
            Log.d(TAG, "Submitted! Saving data...")
            val habit = Habit(
                0, binding.nameCreate.text.toString(),
                binding.descCreate.text.toString(),
                binding.createPriority.rating,
                binding.createReminder.text.toString(),
                false,
                0
            )

            listViewModel.addHabit(habit)
            viewModel.setCurHabit(habit)
            Log.d(TAG, "New Habit saved with id ${habit.id}: \n" + viewModel.curHabit.value?.toString())
        }

        if (view.id == R.id.cancel){
            Log.d(TAG, "OPERATION CANCELLED - No data saved")
        }

        Log.d(TAG, "Navigating back to Habit List...")
        findNavController().navigate(R.id.action_SecondFragment_to_FirstFragment)
    }
}
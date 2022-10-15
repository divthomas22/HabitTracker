package com.example.habittracker.fragments

import android.content.ContentValues
import android.content.ContentValues.TAG
import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.habittracker.viewmodel.HabitListViewModel
import com.example.habittracker.R
import com.example.habittracker.adapters.HabitListAdapter
import com.example.habittracker.databinding.HabitListBinding
import com.example.habittracker.datalayer.Habit
import com.example.habittracker.viewmodel.CurHabitViewModel

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class HabitList : Fragment(), View.OnClickListener {

    private var _binding: HabitListBinding? = null
    private val binding get() = _binding!!
    private var columnCount = 1

    private lateinit var myAdapter: HabitListAdapter
    private lateinit var viewModel: CurHabitViewModel
    private lateinit var listViewModel: HabitListViewModel
    private lateinit var onHabitClickListener: HabitListAdapter.OnHabitClickListener

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = HabitListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is HabitListAdapter.OnHabitClickListener) {
            onHabitClickListener = context
        } else {
            throw RuntimeException("Must implement OnHabitClickListener")
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel =
            ViewModelProvider(requireActivity()).get(CurHabitViewModel::class.java)
        listViewModel =
            ViewModelProvider(this).get(HabitListViewModel::class.java)

        binding.recyclerView.apply{
            layoutManager = when {
                columnCount <= 1 -> LinearLayoutManager(context)
                else -> GridLayoutManager(context, columnCount)
            }
            myAdapter = HabitListAdapter(
                object : HabitListAdapter.OnHabitClickListener {
                    override fun onHabitClick(habit: Habit) {
                        viewModel.setCurHabit(habit)
                        Log.d(TAG, "Setting curHabit to: \n${habit}")
                        viewModel.updateCurHabit(
                            habit.name,
                            habit.description,
                            habit.priority,
                            habit.remindTime,
                            habit.isComplete,
                            habit.streak)
                        onHabitClickListener.onHabitClick(habit)
                    }
                }
            )
            this.adapter = myAdapter

            listViewModel.habitList.observe(viewLifecycleOwner, Observer {
                myAdapter.replaceItems(it)
                Log.d(TAG, "Item Count:" + myAdapter.itemCount.toString())
                viewModel.initCurHabit(myAdapter.getHabit(0))
            })

            viewModel.curHabit.observe(viewLifecycleOwner, Observer {
                myAdapter.notifyDataSetChanged()
            })
        }

        binding.addHabit.setOnClickListener {
            Log.d(TAG, "Create Habit Button selected.")
            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
        }

        binding.refresh.setOnClickListener (this)

//        if (myAdapter.itemCount > 0){
//            binding.emptyView.text = ""
//        } else {
//            binding.emptyView.text = "No Habits yet! Create one! ->"
//        }

    }

    override fun onClick(view: View) {
        if (view.id == R.id.refresh) {
            Log.d("Refresh Status", "Refreshing to a new day...")
            val count = myAdapter.itemCount
            if (count > 0) {
                for (i in 0 until count){
                    val habit = myAdapter.getHabit(i)
                    viewModel.setCurHabit(habit)
                    if (habit.isComplete){   //habit was completed today
                        viewModel.updateCurHabit(
                            habit.name,
                            habit.description,
                            habit.priority,
                            habit.remindTime,
                            false,
                            habit.streak + 1
                        )
                        Log.d("Refresh Status", "Habit ${habit.id} streak updated.")
                    }
                    else { //streak is broken
                        viewModel.updateCurHabit(
                            habit.name,
                            habit.description,
                            habit.priority,
                            habit.remindTime,
                            false,
                            0
                        )
                        Log.d("Refresh Status", "Habit ${habit.id} streak broken.")
                    }
                    Log.d("Updated Habit", "${viewModel.curHabit.value}")
                }
            }
            else {
                Log.d("Refresh Status", "NO HABITS SAVED.")
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }



}
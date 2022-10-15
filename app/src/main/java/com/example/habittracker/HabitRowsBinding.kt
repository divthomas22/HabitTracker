package com.example.habittracker

import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.habittracker.databinding.HabitRowsBinding

class EditFragment : Fragment() {
    // use ViewBinding
    private var _binding: HabitRowsBinding? = null
    // This property is only valid between onCreateView and onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Use ViewBinding
        // FragmentDetailBinding is a generated binding class mapped to fragment_detail.xml
        _binding = HabitRowsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view,savedInstanceState)
        binding.habitItemName.text
    }
}

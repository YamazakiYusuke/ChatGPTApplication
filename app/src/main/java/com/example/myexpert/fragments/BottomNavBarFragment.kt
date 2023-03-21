package com.example.myexpert.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.myexpert.R
import com.example.myexpert.activities.MainActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BottomNavBarFragment : Fragment() {
    companion object {
        private var selectedId: Int = R.id.menu_selection
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_bottom_nav_bar, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val bottomNav = view.findViewById<BottomNavigationView>(R.id.bottom_nav_bar)
        bottomNav.selectedItemId = selectedId
        bottomNav.setOnItemSelectedListener { item ->
            selectedId = item.itemId
            when (item.itemId) {
                R.id.menu_selection -> {
                    replaceFragment(SelectExpertFragment())
                }
                R.id.menu_history -> {
                    replaceFragment(ChatHistoryFragment())
                }
            }
            return@setOnItemSelectedListener true
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        if (activity is MainActivity) {
            (activity as MainActivity).replaceFragment(fragment)
        }
    }
}
package com.example.myexpert.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.myexpert.R
import com.example.myexpert.activity.MainActivity
import com.google.android.material.bottomnavigation.BottomNavigationView


class BottomNavBarFragment : Fragment() {

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
        bottomNav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.menu_selection -> {
                    replaceFragment(SelectExpertFragment())
                }
                R.id.menu_history -> {
                    replaceFragment(SetSecretKeyFragment())
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
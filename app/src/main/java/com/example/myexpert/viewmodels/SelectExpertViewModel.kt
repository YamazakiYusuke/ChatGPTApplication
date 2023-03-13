package com.example.myexpert.viewmodels

import androidx.lifecycle.ViewModel
import com.example.myexpert.enums.Expert

class SelectExpertViewModel : ViewModel() {
    fun getRowsData(): ArrayList<Array<Expert>> {
        val data: ArrayList<Array<Expert>> = arrayListOf()
        for (i in 1..3) {
            data.add(Expert.values())
        }
        return data
    }
}
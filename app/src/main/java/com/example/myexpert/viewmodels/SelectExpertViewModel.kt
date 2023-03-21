package com.example.myexpert.viewmodels

import androidx.lifecycle.ViewModel
import com.example.myexpert.R
import com.example.myexpert.enums.Expert
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SelectExpertViewModel @Inject constructor(): ViewModel() {
    fun getRowsData(): Map<Int, List<Expert>> {
        return Expert.values().groupBy {
            when (it.categoryId) {
                R.string.teacher -> it.categoryId
                R.string.medical -> it.categoryId
                R.string.it -> it.categoryId
                R.string.specialist -> it.categoryId
                R.string.global -> it.categoryId
                else -> -1
            }
        }
    }
}
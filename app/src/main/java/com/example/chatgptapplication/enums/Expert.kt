package com.example.chatgptapplication.enums

import com.example.chatgptapplication.R

enum class Expert(val imageId: Int, val titleId: Int) {
    SCHOOL_TEACHER(R.drawable.school_teacher, R.string.school_teacher),
    LAWYER(R.drawable.lawyer, R.string.lawyer),
    SOFTWARE_ENGINEER(R.drawable.software_engineer, R.string.software_engineer),
    DOCTOR(R.drawable.doctor, R.string.doctor)
}
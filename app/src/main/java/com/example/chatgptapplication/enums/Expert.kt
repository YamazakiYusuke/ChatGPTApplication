package com.example.chatgptapplication.enums

import com.example.chatgptapplication.R

enum class Expert(val imageId: Int, val titleId: Int) {
    SCHOOL_TEACHER(R.drawable.school_teacher, R.string.school_teacher),
    LAWYER(R.drawable.lawyer, R.string.lawyer),
    SOFTWARE_ENGINEER(R.drawable.software_engineer, R.string.software_engineer),
    DOCTOR(R.drawable.doctor, R.string.doctor),
    PSYCHOLOGICAL_COUNSELOR(R.drawable.psychological_counselor, R.string.psychological_counselor),
    ACCOUNTANT(R.drawable.accountant, R.string.accountant),
    DENTIST(R.drawable.dentist, R.string.dentist),
    ENGLISH_TEACHER(R.drawable.english_teacher, R.string.english_teacher),
    DIETITIAN(R.drawable.dentist, R.string.dentist),
    FARMER(R.drawable.farmer, R.string.farmer)
}
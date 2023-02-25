package com.example.chatgptapplication

import android.app.Application
import androidx.room.Room
import com.example.chatgptapplication.database.AppDatabase

class MainApplication : Application() {
    companion object {
        lateinit var database: AppDatabase
    }

    override fun onCreate() {
        super.onCreate()
        database = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "app_database"
        ).build()
    }
}
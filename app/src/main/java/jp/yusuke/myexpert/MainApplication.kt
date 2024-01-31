package jp.yusuke.myexpert

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import androidx.room.Room
import jp.yusuke.myexpert.database.AppDatabase
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MainApplication : Application() {
    companion object {
        lateinit var database: AppDatabase
    }

    override fun onCreate() {
        super.onCreate()
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        jp.yusuke.myexpert.MainApplication.Companion.database = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "app_database"
        ).build()
    }
}
package com.example.chatgptapplication.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.chatgptapplication.database.dao.ChatDao
import com.example.chatgptapplication.database.table.Chat

@Database(entities = [Chat::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun chatDao(): ChatDao
}
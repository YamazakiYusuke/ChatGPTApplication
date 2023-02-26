package com.example.myexpert.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.myexpert.database.dao.ChatDao
import com.example.myexpert.database.table.Chat

@Database(entities = [Chat::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun chatDao(): ChatDao
}
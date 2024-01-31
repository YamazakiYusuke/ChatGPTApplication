package jp.yusuke.myexpert.database

import androidx.room.Database
import androidx.room.RoomDatabase
import jp.yusuke.myexpert.database.dao.ChatDao
import jp.yusuke.myexpert.database.dao.ChatThreadDao
import jp.yusuke.myexpert.database.table.Chat
import jp.yusuke.myexpert.database.table.ChatThread

@Database(entities = [Chat::class, ChatThread::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun chatDao(): ChatDao
    abstract fun chatThreadDao(): ChatThreadDao
}
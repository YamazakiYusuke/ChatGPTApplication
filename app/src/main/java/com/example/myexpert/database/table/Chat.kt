package com.example.myexpert.database.table

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "chat")
data class Chat(
    @PrimaryKey
    val id: String = UUID.randomUUID().toString(),
    // 会話1つに割り当てるID
    val chat_id: String,
    // Userからの質問
    val prompt: String,
    // ChatGPTからの返答
    val response: String,
    // 作成時間
    val created_at: Long,
)

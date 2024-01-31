package jp.yusuke.myexpert.database.table

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDateTime
import java.time.ZoneOffset

@Entity(tableName = "chat_thread")
data class ChatThread(
    @PrimaryKey
    val chat_id: String,
    val expert_image_id: Int,
    val expert_name_id: Int,
    val init_question: String,
    val created_at: Long = LocalDateTime.now().toEpochSecond(ZoneOffset.UTC),
)
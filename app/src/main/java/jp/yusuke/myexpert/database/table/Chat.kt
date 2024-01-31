package jp.yusuke.myexpert.database.table

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.util.*

@Entity(tableName = "chat")
data class Chat(
    @PrimaryKey
    val id: String = UUID.randomUUID().toString(),
    // 会話1つに割り当てるID
    val chat_id: String,
    // 役割
    val role: String,
    // メッセージ
    val content: String,
    // 作成時間
    val created_at: Long = LocalDateTime.now().toEpochSecond(ZoneOffset.UTC),
)

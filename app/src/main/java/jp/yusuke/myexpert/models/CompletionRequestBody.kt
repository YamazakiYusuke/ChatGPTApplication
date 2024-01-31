package jp.yusuke.myexpert.models

import com.google.gson.annotations.SerializedName

data class CompletionRequestBody(
    // 使用するGPT-3モデルを指定する識別子
    @SerializedName("model") val model: String,
    @SerializedName("messages") val messages: List<Message>,
)

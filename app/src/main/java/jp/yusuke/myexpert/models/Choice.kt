package jp.yusuke.myexpert.models

import com.google.gson.annotations.SerializedName

data class Choice(
    @SerializedName("message") val message: Message,
    @SerializedName("index") val index: Int,
    @SerializedName("finish_reason") val finishReason: String
)

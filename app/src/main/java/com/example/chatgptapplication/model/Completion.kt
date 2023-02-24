package com.example.chatgptapplication.model

import com.google.gson.annotations.SerializedName

data class Completion(
    @SerializedName("text") val text: String,
    @SerializedName("index") val index: Int,
    @SerializedName("logprobs") val logprobs: Any?,
    @SerializedName("finish_reason") val finishReason: String
)
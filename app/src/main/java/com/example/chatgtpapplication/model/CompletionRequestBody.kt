package com.example.chatgtpapplication.model

import com.google.gson.annotations.SerializedName

data class CompletionRequestBody(
    @SerializedName("prompt") val prompt: String,
    @SerializedName("temperature") val temperature: Float,
    @SerializedName("max_tokens") val maxTokens: Int,
    @SerializedName("n") val n: Int,
    @SerializedName("stop") val stop: List<String>? = null,
    @SerializedName("model") val model: String
)

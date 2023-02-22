package com.example.chatgtpapplication.model

import com.google.gson.annotations.SerializedName

data class CompletionResponse(
    @SerializedName("id") val id: String,
    @SerializedName("created") val created: Long,
    @SerializedName("model") val model: String,
    @SerializedName("choices") val completions: List<Completion>
)

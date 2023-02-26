package com.example.myexpert.model

import com.google.gson.annotations.SerializedName

data class CompletionRequestBody(
    // GPT-3に提示するテキストのプロンプト
    @SerializedName("prompt") val prompt: String,
    // 生成されるテキストのランダム性を制御する値
    @SerializedName("temperature") val temperature: Float,
    // GPT-3が生成するテキストの最大長
    @SerializedName("max_tokens") val maxTokens: Int,
    // 複数の応答を生成する場合の応答の数。デフォルト値は1
    @SerializedName("n") val n: Int = 1,
    // 生成されたテキストの終了条件を示すトークン
    @SerializedName("stop") val stop: List<String>? = null,
    // 使用するGPT-3モデルを指定する識別子
    @SerializedName("model") val model: String,
)

package jp.yusuke.myexpert.models

import com.google.gson.annotations.SerializedName

data class CompletionResponse(
    // リクエストに割り当てられた一意のID
    @SerializedName("id") val id: String,
    // リクエストが作成された時間
    @SerializedName("created") val created: Long,
    // 使用されたGPT-3モデルの識別子
    @SerializedName("model") val model: String,
    // 生成されたテキストのリスト。リクエストパラメーターのnの値に応じて、複数の応答が含まれる場合がある。
    @SerializedName("choices") val choices: List<Choice>,
//    // 生成されたテキストのリスト。リクエストパラメーターのnの値に応じて、複数の応答が含まれる場合がある。
//    @SerializedName("completions") val completions: List<Completion>,
//    // 以前の応答から生成されたコンテキスト
//    @SerializedName("context") val context: String,
)
